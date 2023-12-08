package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alice.novel.module.common.dto.param.HTSReptileInfoParamDTO;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.entity.*;
import com.alice.novel.module.common.mapper.NovelChapterMapper;
import com.alice.novel.module.common.mapper.ReptileJobDetailMapper;
import com.alice.novel.module.common.mapper.ReptileJobMapper;
import com.alice.novel.module.novel.service.HTSService;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.util.ChineseAndArabicNumUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 和图书爬虫
 * @DateTime 2023/12/5 14:28
 */
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
        String result = HttpUtil.get(url);
        return getData(result);
    }

    /**
     * 根据html文本提取小说信息
     *
     * @param html 完整html文本
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getData(String html) {
        return null;
    }

    /**
     * 获取小说每个章节的链接
     *
     * @param baseUrl 链接
     * @param novelNumber 小说编号
     * @return List<ReptileDetailInfo> 小说章节链接信息
     */
    @Override
    public List<ReptileJobDetailResultDTO> getNovelChapterLink(String baseUrl, String novelNumber) {
        String url = baseUrl + "/" + novelNumber + "/dir.json";
        String result = HttpUtil.get(url);
        JSONArray dirArray = JSON.parseArray(result);
        List<ReptileJobDetailResultDTO> reptileJobDetailArrayList = new ArrayList<>(3000);
        for (Object dir : dirArray) {
            ReptileJobDetailResultDTO reptileJobDetailResultDTO = new ReptileJobDetailResultDTO();
            JSONArray tempArray = JSON.parseArray(dir.toString());
            // 判断是否为卷名
            String str0 = tempArray.getString(0);
            if (SysConstants.STRING_DT.equals(str0)) {
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
     * @param novelInfo 小说信息
     * @param reptileJobDetailResultDTOList 爬虫任务明细信息
     */
    @Async("novelReptileExecutor")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveChapterInfo(NovelInfo novelInfo, List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList) {
        List<NovelChapter> novelChapterList = new ArrayList<>(SysConstants.MAX_BATCH + 10);
        for (ReptileJobDetailResultDTO reptileJobDetailResultDTO : reptileJobDetailResultDTOList) {
            UpdateWrapper<ReptileJobDetail> reptileJobDetailUpdateWrapper = new UpdateWrapper<>();
            reptileJobDetailUpdateWrapper.eq("ID", reptileJobDetailResultDTO.getReptileJobDetailId());
            ReptileJobDetail reptileJobDetail = new ReptileJobDetail();
            NovelChapter novelChapter = new NovelChapter();
            BeanUtil.copyProperties(reptileJobDetailResultDTO, novelChapter);
            novelChapter.setNovelInfoId(novelInfo.getId());
            Map<String, String> resultMap = getNovelInfo(reptileJobDetailResultDTO.getReptileUrl());
            if (SysConstants.NUMBER_ONE.equals(resultMap.get("code"))) {
                String content = resultMap.get("content");
                novelChapter.setChapterContent(content);
                reptileJobDetailUpdateWrapper.set("DONE_FLAG", SysConstants.IS_YES);
            } else {
                reptileJobDetailUpdateWrapper.set("DONE_FLAG", SysConstants.IS_NO);
                reptileJobDetailUpdateWrapper.set("ERROR_MSG", resultMap.get("msg"));
                reptileJobDetail.setId(reptileJobDetailResultDTO.getReptileJobDetailId());
            }
            novelChapterList.add(novelChapter);
            reptileJobDetailMapper.update(null, reptileJobDetailUpdateWrapper);
        }
        novelChapterMapper.insertBatchSomeColumn(novelChapterList);
    }
    
    public static void main(String[] args) {
        String result = HttpUtil.get("https://www.hetushu.com/book/38/24991.html");

//        String result = HttpUtil.get("https://www.hetushu.com/book/38/dir.json");
//        JSONArray dirArray = JSON.parseArray(result);
//        for (Object dir : dirArray) {
//            JSONArray tempArray = JSON.parseArray(dir.toString());
//            System.out.println(tempArray.getString(0));
//            System.out.println(tempArray.getString(1));
//            if (tempArray.size() > 2) {
//                System.out.println(tempArray.getString(2));
//            }
//        }
//        String chapterName = "二千四百四十五 飞升仙界（大结局）";
//        int start = chapterName.indexOf("第") + 1;
//        int end = chapterName.indexOf("章");
//        if (start < end) {
//            System.out.println(ChineseAndArabicNumUtil.chineseNumToArabicNum(chapterName.substring(start, end)));
//        } else {
//            System.out.println(-1);
//        }
    }
}
