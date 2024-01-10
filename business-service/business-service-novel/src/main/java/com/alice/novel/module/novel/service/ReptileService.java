package com.alice.novel.module.novel.service;

import com.alice.novel.module.common.dto.param.ReptileInfoCommonDTO;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.dto.result.ReptileJobResultDTO;
import com.alice.novel.module.common.entity.NovelInfo;

import java.util.List;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:27
 */
public interface ReptileService {

    /**
     * 保存爬虫任务
     *
     * @param reptileInfoParamDTO 爬虫信息
     * @param tClass              Class<?>
     */
    ReptileJobResultDTO saveReptileJob(ReptileInfoCommonDTO reptileInfoParamDTO, Class<?> tClass);

    /**
     * 保存章节信息
     *
     * @param novelInfo                     小说信息
     * @param reptileJobDetailResultDTOList 爬虫任务明细信息
     * @param tClass                        Class<?>
     */
    void saveChapterInfo(NovelInfo novelInfo, List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList, Class<?> tClass);

}
