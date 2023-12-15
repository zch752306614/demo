package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alice.novel.module.common.dto.param.ReptileInfoCommonDTO;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.entity.*;
import com.alice.novel.module.common.service.NovelChapterService;
import com.alice.novel.module.common.service.ReptileJobDetailService;
import com.alice.novel.module.common.service.ReptileJobService;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.novel.module.novel.service.reptile.CommonReptileService;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:27
 */
@Slf4j
@Service
public class ReptileServiceImpl implements ReptileService {

    @Resource
    private ReptileJobService reptileJobService;
    @Resource
    private ReptileJobDetailService reptileJobDetailService;
    @Resource
    private NovelChapterService novelChapterService;


    /**
     * 保存爬虫任务
     *
     * @param reptileInfoParamDTO 爬虫信息
     * @param tClass              Class<?>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ReptileJobDetailResultDTO> saveReptileJob(ReptileInfoCommonDTO reptileInfoParamDTO, Class<?> tClass) {
        CommonReptileService commonReptileService = (CommonReptileService) SpringUtil.getBean(tClass);
        String baseUrl = reptileInfoParamDTO.getBaseUrl();
        String midUrl = reptileInfoParamDTO.getMidUrl();
        String novelNumber = reptileInfoParamDTO.getNovelNumber();
        List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList = commonReptileService.getNovelChapterLink(baseUrl, midUrl, novelNumber);
        ReptileJob reptileJob = new ReptileJob();
        BeanUtil.copyProperties(reptileInfoParamDTO, reptileJob);
        // 保存任务信息
        reptileJobService.save(reptileJob);
        // 保存任务明细信息
        for (ReptileJobDetailResultDTO reptileJobDetailResultDTO : reptileJobDetailResultDTOList) {
            reptileJobDetailResultDTO.setReptileJobId(reptileJob.getId());
        }
        return reptileJobDetailResultDTOList;
    }

    /**
     * 保存章节信息
     *
     * @param novelInfo                     小说信息
     * @param reptileJobDetailResultDTOList 爬虫任务明细信息
     * @param tClass                        Class<?>
     */
    @Override
    @Async("novelReptileExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void saveChapterInfo(NovelInfo novelInfo, List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList, Class<?> tClass) {
        CommonReptileService commonReptileService = (CommonReptileService) SpringUtil.getBean(tClass);
        List<NovelChapter> novelChapterList = new ArrayList<>(SysConstants.MAX_BATCH + 10);
        List<ReptileJobDetail> reptileJobDetailList = new ArrayList<>(SysConstants.MAX_BATCH + 10);
        for (ReptileJobDetailResultDTO reptileJobDetailResultDTO : reptileJobDetailResultDTOList) {
            NovelChapter novelChapter = new NovelChapter();
            ReptileJobDetail reptileJobDetail = new ReptileJobDetail();
            BeanUtil.copyProperties(reptileJobDetailResultDTO, novelChapter);
            BeanUtil.copyProperties(reptileJobDetailResultDTO, reptileJobDetail);
            novelChapter.setNovelInfoId(novelInfo.getId());
            Map<String, String> resultMap = commonReptileService.getNovelInfo(reptileJobDetailResultDTO.getReptileUrl());
            if (SysConstants.CODE_SUCCESS.equals(resultMap.get("code"))) {
                String content = resultMap.get("content");
                novelChapter.setChapterContent(content);
                novelChapter.setChapterWordsCount(content.length());
                reptileJobDetail.setDoneFlag(SysConstants.IS_YES);
            } else {
                String msg = resultMap.get("msg");
                reptileJobDetail.setDoneFlag(SysConstants.IS_NO);
                if (ObjectUtil.isNotEmpty(msg)) {
                    reptileJobDetail.setErrorMsg(msg.substring(0, Math.min(msg.length(), SysConstants.MSG_MAX_LEN)));
                }
            }
            novelChapterList.add(novelChapter);
            reptileJobDetailList.add(reptileJobDetail);
            log.info(String.format("爬取成功url=%s", reptileJobDetailResultDTO.getReptileUrl()));
        }
        try {
            novelChapterService.saveBatch(novelChapterList);
        } catch (Exception ex) {
            for (ReptileJobDetail reptileJobDetail : reptileJobDetailList) {
                String errMsg = ex.getMessage();
                reptileJobDetail.setDoneFlag(SysConstants.IS_NO);
                reptileJobDetail.setErrorMsg(errMsg.substring(0, Math.min(errMsg.length(), SysConstants.MSG_MAX_LEN)));
            }
        }

        reptileJobDetailService.saveBatch(reptileJobDetailList);
        log.info("本批次爬取结束");
    }

}
