package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alice.novel.module.common.dto.param.HTSReptileInfoParamDTO;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.entity.ReptileJob;
import com.alice.novel.module.common.entity.ReptileJobDetail;
import com.alice.novel.module.common.mapper.NovelChapterMapper;
import com.alice.novel.module.common.mapper.ReptileJobDetailMapper;
import com.alice.novel.module.common.mapper.ReptileJobMapper;
import com.alice.novel.module.novel.service.HTSService;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.util.ChineseAndArabicNumUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 和图书爬虫
 * @DateTime 2023/12/5 14:28
 */
@Slf4j
@Service("hTSService")
public class HTSServiceImpl implements HTSService {

    @Resource
    private ReptileJobMapper reptileJobMapper;
    @Resource
    private ReptileJobDetailMapper reptileJobDetailMapper;
    @Resource
    private NovelChapterMapper novelChapterMapper;

    /**
     * 提取信息
     *
     * @param url String 爬取小说章节的路径
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getNovelInfo(String url) {
        Map<String, String> result = new HashMap<>(10);
        try {
            String htmlString = HttpUtil.get(url);
            result = getData(htmlString);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("code", SysConstants.CODE_FAIL);
            result.put("msg", ex.getMessage());
        }
        return result;
    }

    /**
     * 根据html文本提取小说信息
     *
     * @param htmlString 完整html文本
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getData(String htmlString) {
        // 获取的数据，存放在集合中
        Map<String, String> result = new HashMap<>(10);
        try {
            // 使用Jsoup解析HTML
            Document document = Jsoup.parse(htmlString);
            // 查找id为"content"下的所有div元素
            Elements contentDivs = document.getElementById("content").getElementsByTag("div");
            // 提取小说正文内容
            StringBuilder novelContent = new StringBuilder();
            for (Element contentDiv : contentDivs) {
                // 排除<dfn>, <code>, <tt>, <samp>等标签下的内容
                contentDiv.select("dfn,code,tt,samp,kbd,s,var,u,bdo").remove();
                novelContent.append(contentDiv.text()).append("\n");
            }
            // 获取小说正文
            result.put("code", SysConstants.CODE_SUCCESS);
            result.put("content", novelContent.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("code", SysConstants.CODE_FAIL);
            result.put("msg", ex.getMessage());
        }
        return result;
    }

    /**
     * 获取小说每个章节的链接
     *
     * @param baseUrl     链接
     * @param novelNumber 小说编号
     * @return List<ReptileDetailInfo> 小说章节链接信息
     */
    @Override
    public List<ReptileJobDetailResultDTO> getNovelChapterLink(String baseUrl, String novelNumber) {
        String url = baseUrl + "/" + novelNumber + "/dir.json";
        String result = HttpUtil.get(url);
        JSONArray dirArray = JSON.parseArray(result);
        List<ReptileJobDetailResultDTO> reptileJobDetailArrayList = new ArrayList<>(3000);
        String chapterPart = "";
        for (Object dir : dirArray) {
            ReptileJobDetailResultDTO reptileJobDetailResultDTO = new ReptileJobDetailResultDTO();
            JSONArray tempArray = JSON.parseArray(dir.toString());
            // 判断是否为卷名
            String str0 = tempArray.getString(0);
            if (SysConstants.STRING_DT.equals(str0)) {
                chapterPart = tempArray.getString(1);
                reptileJobDetailResultDTO.setChapterName(tempArray.getString(1));
                reptileJobDetailResultDTO.setChapterNumber(-1);
            } else if (SysConstants.STRING_DD.equals(str0)) {
                String chapterName = tempArray.getString(1);
                reptileJobDetailResultDTO.setChapterName(chapterName);
                int start = chapterName.indexOf("第") + 1;
                int end = chapterName.indexOf("章");
                if (start < end && chapterName.contains("第") && chapterName.contains("章")) {
                    reptileJobDetailResultDTO.setChapterNumber(ChineseAndArabicNumUtil.chineseNumToArabicNum(chapterName.substring(start, end)));
                } else {
                    reptileJobDetailResultDTO.setChapterNumber(-1);
                }
            }
            // 判断是否存在路径
            if (tempArray.size() > 2 && ObjectUtil.isNotEmpty(tempArray.getString(2))) {
                String reptileUrl = baseUrl + "/" + novelNumber + "/" + tempArray.getString(2) + ".html";
                reptileJobDetailResultDTO.setChapterPart(chapterPart);
                reptileJobDetailResultDTO.setReptileUrl(reptileUrl);
                reptileJobDetailArrayList.add(reptileJobDetailResultDTO);
            }
        }
        return reptileJobDetailArrayList;
    }

    /**
     * 保存爬虫任务
     *
     * @param reptileInfoParamDTO 任务信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ReptileJobDetailResultDTO> saveReptileJob(HTSReptileInfoParamDTO reptileInfoParamDTO) {
        String baseUrl = reptileInfoParamDTO.getBaseUrl();
        String novelNumber = reptileInfoParamDTO.getNovelNumber();
        List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList = this.getNovelChapterLink(baseUrl, novelNumber);
        List<ReptileJobDetail> reptileJobDetailList = new ArrayList<>(3000);
        ReptileJob reptileJob = new ReptileJob();
        BeanUtil.copyProperties(reptileInfoParamDTO, reptileJob);
        // 保存任务信息
        reptileJobMapper.insert(reptileJob);
        // 保存任务明细信息
        for (ReptileJobDetailResultDTO reptileJobDetailResultDTO : reptileJobDetailResultDTOList) {
            ReptileJobDetail reptileJobDetail = new ReptileJobDetail();
            reptileJobDetail.setReptileJobId(reptileJob.getId());
            reptileJobDetail.setReptileUrl(reptileJobDetailResultDTO.getReptileUrl());
            reptileJobDetail.setDoneFlag(SysConstants.IS_NO);
            reptileJobDetailList.add(reptileJobDetail);
        }
        reptileJobDetailMapper.insertBatchSomeColumn(reptileJobDetailList);
        return reptileJobDetailResultDTOList;
    }

    /**
     * 保存小说信息
     *
     * @param reptileInfoParamDTO 爬虫信息
     */
    @Override
    public void saveNovelInfo(HTSReptileInfoParamDTO reptileInfoParamDTO) {

    }

    /**
     * 保存章节信息
     *
     * @param novelInfo                     小说信息
     * @param reptileJobDetailResultDTOList 爬虫任务明细信息
     */
    @Async("novelReptileExecutor")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveChapterInfo(NovelInfo novelInfo, List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList) {
        List<NovelChapter> novelChapterList = new ArrayList<>(SysConstants.MAX_BATCH + 10);
        for (ReptileJobDetailResultDTO reptileJobDetailResultDTO : reptileJobDetailResultDTOList) {
            String doneFlag;
            String errorMsg = "";
            ReptileJobDetail reptileJobDetail = new ReptileJobDetail();
            NovelChapter novelChapter = new NovelChapter();
            BeanUtil.copyProperties(reptileJobDetailResultDTO, novelChapter);
            novelChapter.setNovelInfoId(novelInfo.getId());
            Map<String, String> resultMap = getNovelInfo(reptileJobDetailResultDTO.getReptileUrl());
            if (SysConstants.CODE_SUCCESS.equals(resultMap.get("code"))) {
                String content = resultMap.get("content");
                novelChapter.setChapterContent(content);
                novelChapter.setChapterWordsCount(content.length());
                doneFlag = SysConstants.IS_YES;
            } else {
                String msg = resultMap.get("msg");
                doneFlag = SysConstants.IS_NO;
                if (ObjectUtil.isNotEmpty(msg)) {
                    errorMsg = msg.substring(0, Math.min(msg.length(), SysConstants.MSG_MAX_LEN));
                }
                reptileJobDetail.setId(reptileJobDetailResultDTO.getReptileJobDetailId());
            }
            novelChapterList.add(novelChapter);
            UpdateWrapper<ReptileJobDetail> reptileJobDetailUpdateWrapper = new UpdateWrapper<>();
            reptileJobDetailUpdateWrapper.eq("ID", reptileJobDetailResultDTO.getReptileJobDetailId())
                    .set("DONE_FLAG", doneFlag)
                    .set("ERROR_MSG", errorMsg);
            reptileJobDetailMapper.update(null, reptileJobDetailUpdateWrapper);
            log.info(String.format("爬取成功url=%s", reptileJobDetailResultDTO.getReptileUrl()));
        }
        novelChapterMapper.insertBatchSomeColumn(novelChapterList);
        log.info("本批次爬取结束");
    }

    public static void main(String[] args) {
        String htmlString = HttpUtil.get("https://www.hetushu.com/book/38/24991.html");

        // 使用Jsoup解析HTML
        Document document = Jsoup.parse(htmlString);

        // 查找id为"content"下的所有div元素
        Elements contentDivs = document.select("#content div");

        // 提取小说正文内容
        StringBuilder novelContent = new StringBuilder();
        for (Element contentDiv : contentDivs) {
            // 排除<dfn>, <code>, <tt>, <samp>等标签下的内容
//            contentDiv.select("dfn, code, tt, samp").remove();
            novelContent.append(contentDiv.text()).append("\n");
        }

        // 打印提取的小说正文
        System.out.println(novelContent);
    }
}
