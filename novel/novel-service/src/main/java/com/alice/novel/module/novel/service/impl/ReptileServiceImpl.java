package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alice.novel.module.common.dao.NovelChapterDao;
import com.alice.novel.module.common.dao.NovelInfoDao;
import com.alice.novel.module.common.dao.ReptileDetailInfoDao;
import com.alice.novel.module.common.dao.ReptileInfoDao;
import com.alice.novel.module.common.dto.param.ReptileInfoParamDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.entity.ReptileDetailInfo;
import com.alice.novel.module.common.entity.ReptileInfo;
import com.alice.novel.module.novel.service.BQGService;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.redis.service.RedisService;
import com.alice.support.common.util.BusinessExceptionUtil;
import com.alice.support.common.util.QueryWrapperUtil;
import com.alice.support.common.util.StringUtils;
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
    private RedisService redisService;
    @Resource
    private BQGService bqgService;
    @Resource
    private NovelInfoDao novelInfoDao;
    @Resource
    private NovelChapterDao novelChapterDao;
    @Resource
    private ReptileInfoDao reptileInfoDao;
    @Resource
    private ReptileDetailInfoDao reptileDetailInfoDao;

    /**
     * 保存小说明细
     *
     * @param reptileInfo 爬虫任务信息
     * @param novelInfo   小说信息
     */
    @Override
    @Async("novelReptileExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void saveNovelDetails(ReptileInfo reptileInfo, NovelInfo novelInfo) {
        // 保存明细
        int errorCount = 0;
//        int chapterCount;
        int wordCount;
//        String novelName = novelInfo.getNovelName();
//        String novelAuth = novelInfo.getNovelAuthor();
        List<NovelChapter> novelChapterList = new ArrayList<>();
        List<ReptileDetailInfo> reptileDetailInfoList = new ArrayList<>();
        Integer pauseIndex = reptileInfo.getPauseIndex();
        Integer startIndex = reptileInfo.getStartIndex();
        int index = ObjectUtil.isNotEmpty(pauseIndex) ? Math.max(startIndex, pauseIndex) : startIndex;
        int endIndex = reptileInfo.getEndIndex();
        try {
            while (index < endIndex) {
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
                        Map<String, String> data = bqgService.getNovelInfo(url);
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
                        // 记录本次的结果
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
                    Map<String, String> data = bqgService.getNovelInfo(url);
                    title = data.get("title");
                    content.append(data.get("content"));
                    if (ObjectUtil.isEmpty(title) || ObjectUtil.isEmpty(content)
                            || SysConstants.TITLE_ERROR_FILTER.equals(title)
                            || SysConstants.CONTENT_ERROR_FILTER.equals(content.toString())) {
                        errorCount++;
                        index += reptileInfo.getIntervalValue();
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
                    String titleNumber = title.substring(1, title.indexOf("章"));
                    wordCount = content.toString().length();
                    NovelChapter novelChapter = NovelChapter.builder()
                            .chapterContent(content.toString())
                            .chapterName(title)
                            .chapterNumber(StringUtils.convertChineseToArabic(titleNumber))
                            .chapterWordsCount(wordCount)
                            .build();
                    novelChapterList.add(novelChapter);
                }
                index += reptileInfo.getIntervalValue();
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
                reptileDetailInfoDao.insert(reptileDetailInfo);
            }
            // 新增小说章节信息
            for (NovelChapter novelChapter : novelChapterList) {
                novelChapter.setNovelInfoId(novelInfo.getId());
                novelChapterDao.insert(novelChapter);
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
        reptileInfo.setValueFlag(SysConstants.IS_YES);
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
            BeanUtil.copyProperties(reptileInfoParamDTO, novelInfo, "novelChapterCount", "novelWordsCount");
            novelInfoDao.updateById(novelInfo);
        } else {
            // 不存在则新增小说信息
            BeanUtil.copyProperties(reptileInfoParamDTO, novelInfo);
            novelInfo.setNovelWordsCount(0);
            novelInfo.setNovelChapterCount(0);
            novelInfoDao.insert(novelInfo);
        }
        novelInfo.setLastUpdateTime(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        return novelInfo;
    }

    /**
     * 获取任务列表
     *
     * @param reptileInfoParamDTO 任务信息
     * @return List<ReptileInfo>
     */
    @Override
    public List<ReptileInfo> getReptileInfoList(ReptileInfoParamDTO reptileInfoParamDTO) {
        return null;
    }
}
