package com.alice.novel.module.novel.service;

import com.alice.novel.module.common.dto.param.BQGReptileInfoParamDTO;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.entity.ReptileInfo;

import java.util.List;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:27
 */
public interface ReptileService {

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

}
