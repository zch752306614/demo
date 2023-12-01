package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alice.novel.module.common.dao.NovelChapterDao;
import com.alice.novel.module.common.dao.NovelInfoDao;
import com.alice.novel.module.common.dao.ReptileDetailInfoDao;
import com.alice.novel.module.common.dao.ReptileInfoDao;
import com.alice.novel.module.common.dto.param.ReptileInfoParamDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.entity.ReptileDetailInfo;
import com.alice.novel.module.common.entity.ReptileInfo;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.util.BusinessExceptionUtil;
import com.alice.support.common.util.QueryWrapperUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:27
 */
@Service("reptileService")
public class ReptileServiceImpl implements ReptileService {

    @Resource
    private NovelInfoDao novelInfoDao;
    @Resource
    private NovelChapterDao novelChapterDao;
    @Resource
    private ReptileInfoDao reptileInfoDao;
    @Resource
    private ReptileDetailInfoDao reptileDetailInfoDao;

    /**
     * 提取信息
     *
     * @param url String 爬取小说章节的路径
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getNovelInfo(String url) {
        // 结果集合
        Map<String, String> result = new HashMap<>(10);
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            // 模拟浏览器浏览
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:60.0) Gecko/20100101 Firefox/60.0");
            CloseableHttpResponse response = httpclient.execute(httpGet);

            //获取响应状态码
            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            int responseCode = 200;
            //如果状态响应码为200，则获取html实体内容或者json文件
            if (statusCode == responseCode) {
                String html = EntityUtils.toString(entity, Consts.UTF_8);
                // 提取HTML得到商品信息结果
                result = getData(html);
            }
            // 消耗掉实体
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 提取信息
     *
     * @param html 完整html文本
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getData(String html) {
        // 获取的数据，存放在集合中
        Map<String, String> data = new HashMap<>(10);

        // 采用Jsoup解析
        Document doc = Jsoup.parse(html);

        // 获取标题
        Elements content = doc.getElementsByClass("reader-main");
        Elements elements = content.get(0).getElementsByTag("h1");
        String title = elements.text();
        if (title != null) {
            data.put("title", title);
        }

        // 获取正文
        content = doc.getElementsByClass("content");
        elements = content.get(0).getElementsByTag("p");
        StringBuilder text = new StringBuilder();
        for (Element element : elements) {
            String str = element.text();
            str = str.replaceAll("\\s*", "");
            str = str.replaceAll("记住搜索求书阁qiushuge.net提前看书。", "");
            str = str.replaceAll(",", "，");
            str = str.replaceAll("\\.", "。");
            str = str.replaceAll("\"", "” ");
            str = str.replaceAll("!", "！");
            str = str.replaceAll(":", "：");
            str = str.replaceAll("\\?", "？");
            text.append("   ".toCharArray(), 0, 3);
            int len = 0;
            while (len < str.length()) {
                int add = 50;
                if (len == 0) {
                    add = 48;
                }
                text.append(str, len, Math.min(len + add, str.length())).append("\n");
                len += add;
            }
        }
        data.put("content", text.toString());

        //System.out.println("data=" + data);

        //返回的数据
        return data;
    }

    /**
     * 保存小说明细
     *
     * @param reptileInfo 爬虫任务信息
     * @param novelInfo 小说信息
     */
    @Override
    @Async("novelReptileExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void saveNovelDetails(ReptileInfo reptileInfo, NovelInfo novelInfo) {
        // 保存明细
        int errorCount = 0;
        int chapterCount = 0;
        List<NovelChapter> novelChapterList = new ArrayList<>();
        List<ReptileDetailInfo> reptileDetailInfoList = new ArrayList<>();
        Integer pauseIndex = reptileInfo.getPauseIndex();
        Integer startIndex = reptileInfo.getStartIndex();
        int index = ObjectUtil.isNotEmpty(pauseIndex) ? Math.max(startIndex, pauseIndex) : startIndex;
        int endIndex = reptileInfo.getEndIndex();
        try {
            while (index <= endIndex) {
                if (errorCount > 10) {
                    break;
                }
                String url;
                String title = "";
                StringBuilder content = new StringBuilder();
                if (SysConstants.IS_YES.equals(reptileInfo.getPartFlag())) {
                    int partIndex = reptileInfo.getPartStartIndex();
                    boolean firstFlag = true;
                    String preTitle = "";
                    String preContent = "";
                    while (true) {
                        if (!firstFlag) {
                            partIndex += reptileInfo.getPartInterval();
                            url = reptileInfo.getBaseUrl() + index + reptileInfo.getPartSuffix() + partIndex + reptileInfo.getUrlSuffix();
                        } else {
                            url = reptileInfo.getBaseUrl() + index + reptileInfo.getUrlSuffix();
                        }
                        Map<String, String> data = this.getNovelInfo(url);
                        String titlePart = data.get("title");
                        String contentPart = data.get("content");
                        if (ObjectUtil.isEmpty(titlePart) || ObjectUtil.isEmpty(contentPart)
                                || SysConstants.TITLE_ERROR_FILTER.equals(titlePart)
                                || SysConstants.CONTENT_ERROR_FILTER.equals(contentPart)
                                || preTitle.equals(titlePart) || preContent.equals(contentPart)) {
                            break;
                        }
                        // 每章节首次爬取截取标题
                        if (firstFlag) {
                            firstFlag = false;
                            title = titlePart.substring(0, Math.max(titlePart.indexOf(reptileInfo.getTitleSeparator()), 0));
                        }
                        content.append(contentPart);
                        // 记录上一次的结果
                        preTitle = titlePart;
                        preContent = preTitle;
                        // 爬取成功记录路径
                        if (ObjectUtil.isNotEmpty(title) && ObjectUtil.isNotEmpty(content)) {
                            ReptileDetailInfo reptileDetailInfo = new ReptileDetailInfo();
                            reptileDetailInfo.setReptileUrl(url);
                            reptileDetailInfoList.add(reptileDetailInfo);
                            System.out.println(url + title);
                        }
                    }
                } else {
                    url = reptileInfo.getBaseUrl() + index + reptileInfo.getUrlSuffix();
                    Map<String, String> data = getNovelInfo(url);
                    title = data.get("title");
                    content.append(data.get("content"));
                    if (ObjectUtil.isEmpty(title) || ObjectUtil.isEmpty(content)
                            || SysConstants.TITLE_ERROR_FILTER.equals(title)
                            || SysConstants.CONTENT_ERROR_FILTER.equals(content.toString())) {
                        errorCount++;
                        index += reptileInfo.getInterval();
                        continue;
                    }
                    // 记录路径
                    ReptileDetailInfo reptileDetailInfo = new ReptileDetailInfo();
                    if (ObjectUtil.isNotEmpty(title) && ObjectUtil.isNotEmpty(content)) {
                        reptileDetailInfo.setReptileUrl(url);
                        reptileDetailInfoList.add(reptileDetailInfo);
                        System.out.println(url + title);
                    }
                }
                errorCount = 0;
                if (ObjectUtil.isNotEmpty(title) && ObjectUtil.isNotEmpty(content)) {
                    chapterCount++;
                    NovelChapter novelChapter = NovelChapter.builder()
                            .chapterContent(content.toString())
                            .chapterName(title)
                            .chapterNumber(chapterCount)
                            .chapterWordsCount(content.toString().length())
                            .build();
                    novelChapterList.add(novelChapter);
                }
                index += reptileInfo.getInterval();
            }
        } catch (Exception e) {
            reptileInfo.setPauseIndex(index);
            reptileInfo.setDoneFlag(SysConstants.IS_NO);
        } finally {
            // 更新爬虫任务信息
            reptileInfoDao.updateById(reptileInfo);
            // 新增爬虫明细信息
            for (ReptileDetailInfo reptileDetailInfo : reptileDetailInfoList) {
                reptileDetailInfo.setReptileInfoId(reptileInfo.getId());
                reptileInfoDao.insert(reptileInfo);
            }
            // 新增小说章节信息
            for (NovelChapter novelChapter : novelChapterList) {
                novelChapter.setNovelInfoId(novelInfo.getId());
                novelInfoDao.insert(novelInfo);
            }
        }
    }

    /**
     * 保存爬虫任务
     *
     * @param reptileInfoParamDTO 任务信息
     * @return Long 任务ID
     */
    public ReptileInfo saveReptileInfo(ReptileInfoParamDTO reptileInfoParamDTO) {
        // 判断是否存在该小说的任务，每本小说只允许存在一条有效的任务，不存在的话新增一条任务
        ReptileInfo reptileInfoQuery = new ReptileInfo();
        reptileInfoQuery.setNovelName(reptileInfoParamDTO.getNovelName());
        reptileInfoQuery.setNovelAuthor(reptileInfoParamDTO.getNovelAuthor());
        reptileInfoQuery.setValueFlag(SysConstants.IS_YES);
        QueryWrapper<ReptileInfo> jobQueryWrapper = QueryWrapperUtil.initParams(reptileInfoQuery);
        List<ReptileInfo> reptileInfoList = reptileInfoDao.selectList(jobQueryWrapper);
        // 如果任务存在，继续执行当前任务
        ReptileInfo reptileInfo = new ReptileInfo();
        BeanUtil.copyProperties(reptileInfoParamDTO, reptileInfo);
        reptileInfo.setDoneFlag(SysConstants.IS_NO);
        reptileInfo.setStartTime(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        if (ObjectUtil.isNotEmpty(reptileInfoList)) {
            ReptileInfo reptileInfoOld = reptileInfoList.get(0);
            // 如果任务已完成，返回提示
            if (SysConstants.IS_YES.equals(reptileInfo.getDoneFlag())) {
                BusinessExceptionUtil.dataEx("该小说爬取任务已完成，请勿重复执行");
            }
            reptileInfo.setPauseIndex(reptileInfoOld.getPauseIndex());
            // 旧的任务更新成无效
            for (ReptileInfo info : reptileInfoList) {
                info.setValueFlag(SysConstants.IS_NO);
                reptileInfoDao.updateById(info);
            }
        }
        // 新增当前任务
        reptileInfoDao.insert(reptileInfo);
        return reptileInfo;
    }

    /**
     * 保存小说信息
     *
     * @param reptileInfoParamDTO 小说信息
     * @return Long 小说ID
     */
    public NovelInfo saveNovelInfo(ReptileInfoParamDTO reptileInfoParamDTO) {
        // 判断小说是否存在，不存在新增小说信息
        NovelInfo novelInfoQuery = new NovelInfo();
        novelInfoQuery.setNovelName(reptileInfoParamDTO.getNovelName());
        novelInfoQuery.setNovelAuthor(reptileInfoParamDTO.getNovelAuthor());
        QueryWrapper<NovelInfo> novelQueryWrapper = QueryWrapperUtil.initParams(novelInfoQuery);
        List<NovelInfo> novelInfoList = novelInfoDao.selectList(novelQueryWrapper);
        NovelInfo novelInfo = new NovelInfo();
        // 存在则更新小说信息
        if (ObjectUtil.isNotEmpty(novelInfoList)) {
            novelInfo = novelInfoList.get(0);
            BeanUtil.copyProperties(reptileInfoParamDTO, novelInfo);
            novelInfoDao.updateById(novelInfo);
        } else {
            // 不存在则新增小说信息
            BeanUtil.copyProperties(reptileInfoParamDTO, novelInfo);
            novelInfoDao.insert(novelInfo);
        }
        return novelInfo;
    }

    @Override
    public List<ReptileInfo> getReptileInfoList(ReptileInfoParamDTO reptileInfoParamDTO) {
        return null;
    }
}
