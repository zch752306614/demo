package com.alice.novel.module.novel.service;

import com.alice.novel.module.common.dto.param.BQGReptileInfoParamDTO;
import com.alice.novel.module.common.dto.param.ReptileInfoCommonDTO;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.entity.ReptileInfo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:27
 */
public interface ReptileJobService {

    /**
     * 保存小说明细
     *
     * @param reptileInfo 爬虫任务信息
     * @param novelInfo   小说信息
     */
    void saveNovelDetails(ReptileInfo reptileInfo, NovelInfo novelInfo);

    /**
     * 保存爬虫任务
     *
     * @param reptileInfoParamDTO 任务信息
     * @return Long 任务ID
     */
    ReptileInfo saveReptileInfo(BQGReptileInfoParamDTO reptileInfoParamDTO);

    /**
     * 保存小说信息
     *
     * @param reptileInfoParamDTO 小说信息
     * @return Long 小说ID
     */
    NovelInfo saveNovelInfo(BQGReptileInfoParamDTO reptileInfoParamDTO);

    /**
     * 获取任务列表
     *
     * @param reptileInfoParamDTO 任务信息
     * @return List<ReptileInfo>
     */
    List<ReptileInfo> getReptileInfoList(BQGReptileInfoParamDTO reptileInfoParamDTO);

    /**
     * 保存爬虫任务
     *
     * @param reptileInfoParamDTO 爬虫信息
     * @param tClass Class<?>
     */
    List<ReptileJobDetailResultDTO> saveReptileJob(ReptileInfoCommonDTO reptileInfoParamDTO, Class<?> tClass);

    /**
     * 保存章节信息
     *
     * @param novelInfo 小说信息
     * @param reptileJobDetailResultDTOList 爬虫任务明细信息
     * @param tClass Class<?>
     */
    void saveChapterInfo(NovelInfo novelInfo, List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList, Class<?> tClass);

}
