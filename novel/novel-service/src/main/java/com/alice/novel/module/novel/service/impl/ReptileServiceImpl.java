package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alice.novel.module.common.dao.NovelChapterDao;
import com.alice.novel.module.common.dao.NovelInfoDao;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.entity.ReptileInfo;
import com.alice.novel.module.common.dao.ReptileInfoDao;
import com.alice.novel.module.common.dto.param.ReptileInfoParamDTO;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.util.BusinessExceptionUtil;
import com.alice.support.common.util.QueryWrapperUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.springframework.stereotype.Service;

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
     * 保存整本小说
     *
     * @param reptileInfoParamDTO 路径结构信息
     */
    @Override
    public void saveNovel(ReptileInfoParamDTO reptileInfoParamDTO) {
        // 判断是否存在该小说的任务，每本小说只允许存在一条有效的任务
        ReptileInfo reptileInfoQuery = new ReptileInfo();
        reptileInfoQuery.setNovelName(reptileInfoParamDTO.getNovelName());
        reptileInfoQuery.setNovelAuthor(reptileInfoParamDTO.getNovelAuthor());
        reptileInfoQuery.setValueFlag(SysConstants.IS_YES);
        QueryWrapper<ReptileInfo> jobQueryWrapper = QueryWrapperUtil.initParams(reptileInfoQuery);
        List<ReptileInfo> reptileInfoList = reptileInfoDao.selectList(jobQueryWrapper);
        boolean jobFlag = ObjectUtil.isNotEmpty(reptileInfoList);
        // 如果任务存在，继续执行当前任务
        ReptileInfo reptileInfo;
        if (jobFlag) {
            reptileInfo = reptileInfoList.get(0);
            // 如果任务已完成，返回提示
            if (SysConstants.IS_YES.equals(reptileInfo.getDoneFlag())) {
                BusinessExceptionUtil.dataEx("该小说爬取任务已完成，请勿重复执行");
            }
            reptileInfoParamDTO.setBaseUrl(reptileInfo.getBaseUrl());
            if (ObjectUtil.isNotEmpty(reptileInfo.getPauseIndex())) {
                reptileInfoParamDTO.setStartIndex(reptileInfo.getPauseIndex());
            }
        } else {
            reptileInfo = new ReptileInfo();
            BeanUtil.copyProperties(reptileInfoParamDTO, reptileInfo);
        }
        int endIndex = reptileInfoParamDTO.getEndIndex();
        if (ObjectUtil.isEmpty(endIndex)) {
            endIndex = SysConstants.MAX_CHAPTER;
        }
        int errorCount = 0;
        int chapterCount = 0;
        String url = reptileInfoParamDTO.getBaseUrl();
        List<NovelChapter> novelChapterList = new ArrayList<>();
        int index = reptileInfoParamDTO.getStartIndex();
        try {
            while (index <= endIndex) {
                if (errorCount > 10) {
                    break;
                }
                String title = "";
                StringBuilder content = new StringBuilder();
                if (reptileInfoParamDTO.isPartFlag()) {
                    int partIndex = reptileInfoParamDTO.getPartStartIndex();
                    boolean firstFlag = false;
                    String preTitle = "";
                    String preContent = "";
                    while (true) {
                        if (firstFlag) {
                            partIndex += reptileInfoParamDTO.getPartInterval();
                            url += index + reptileInfoParamDTO.getPartSuffix() + partIndex + reptileInfoParamDTO.getUrlSuffix();
                        } else {
                            url += index + reptileInfoParamDTO.getUrlSuffix();
                        }
                        Map<String, String> data = getNovelInfo(url);
                        String titlePart = data.get("title");
                        String contentPart = data.get("content");
                        if (ObjectUtil.isEmpty(titlePart) || ObjectUtil.isEmpty(contentPart)
                                || SysConstants.TITLE_ERROR_FILTER.equals(titlePart)
                                || SysConstants.CONTENT_ERROR_FILTER.equals(contentPart)
                                || preTitle.equals(titlePart) || preContent.equals(contentPart)) {
                            break;
                        }
                        if (!firstFlag) {
                            firstFlag = true;
                            title = titlePart.substring(0, Math.max(titlePart.indexOf(reptileInfoParamDTO.getTitleSeparator()), 0));
                            content.append(contentPart);
                        }
                        content.append(contentPart);
                        // 记录上一次的结果
                        preTitle = titlePart;
                        preContent = preTitle;
                    }
                } else {
                    url += index + reptileInfoParamDTO.getUrlSuffix();
                    Map<String, String> data = getNovelInfo(url);
                    title = data.get("title");
                    content.append(data.get("content"));
                    if (ObjectUtil.isEmpty(title) || ObjectUtil.isEmpty(content)
                            || SysConstants.TITLE_ERROR_FILTER.equals(title)
                            || SysConstants.CONTENT_ERROR_FILTER.equals(content.toString())) {
                        errorCount++;
                        index += reptileInfoParamDTO.getInterval();
                        continue;
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
                System.out.println(url + title);
                index += reptileInfoParamDTO.getInterval();
            }
        } catch (Exception e) {
            reptileInfo.setPauseIndex(index);
            reptileInfo.setDoneFlag(SysConstants.IS_NO);
        } finally {
            // 保存或更新任务信息
            reptileInfo.setDoneFlag(SysConstants.IS_YES);
            reptileInfoDao.insert(reptileInfo);
            // 判断小说是否存在
            NovelInfo novelInfoQuery = new NovelInfo();
            novelInfoQuery.setNovelName(reptileInfoParamDTO.getNovelName());
            novelInfoQuery.setNovelAuthor(reptileInfoParamDTO.getNovelAuthor());
            QueryWrapper<NovelInfo> novelQueryWrapper = QueryWrapperUtil.initParams(novelInfoQuery);
            List<NovelInfo> novelInfoList = novelInfoDao.selectList(novelQueryWrapper);
            NovelInfo novelInfo;
            // 保存或更新小说信息
            if (ObjectUtil.isNotEmpty(novelInfoList)) {
                novelInfo = novelInfoList.get(0);
                novelInfo.setNovelChapterCount(chapterCount);
                novelInfo.setCompletedFlag(reptileInfoParamDTO.getCompletedFlag());
                novelInfoDao.updateById(novelInfo);
            } else {
                novelInfo = NovelInfo.builder()
                        .novelName(reptileInfoParamDTO.getNovelName())
                        .novelAuthor(reptileInfoParamDTO.getNovelAuthor())
                        .novelChapterCount(chapterCount)
                        .completedFlag(reptileInfoParamDTO.getCompletedFlag())
                        .build();
                novelInfoDao.insert(novelInfo);
            }
            // 保存小说章节
            for (NovelChapter chapter : novelChapterList) {
                chapter.setNovelInfoId(novelInfo.getId());
                novelChapterDao.insert(chapter);
            }
        }
    }

    /**
     * 保存爬虫任务
     *
     * @param reptileInfoParamDTO 爬虫信息
     * @return Long 任务ID
     */
    @Override
    public Long saveReptileJob(ReptileInfoParamDTO reptileInfoParamDTO) {
        // 保存爬取小说任务
        ReptileInfo reptileInfo = new ReptileInfo();
        BeanUtil.copyProperties(reptileInfoParamDTO, reptileInfo);
        reptileInfo.setDoneFlag(SysConstants.IS_NO);
        reptileInfo.setStartTime(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        reptileInfoDao.insert(reptileInfo);
        return reptileInfo.getId();
    }

    /**
     * 添加小说
     *
     * @param reptileInfoParamDTO 路径结构信息
     */
    @Override
    public void addNovel(ReptileInfoParamDTO reptileInfoParamDTO) {
        // 保存任务
        Long jobId = saveReptileJob(reptileInfoParamDTO);
        // 保存小说
        saveNovel(reptileInfoParamDTO);
    }

    @Override
    public List<ReptileInfo> getReptileInfoList(ReptileInfoParamDTO reptileInfoParamDTO) {
        return null;
    }
}
