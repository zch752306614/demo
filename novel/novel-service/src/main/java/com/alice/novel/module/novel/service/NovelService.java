package com.alice.novel.module.novel.service;

import com.alice.novel.module.common.dto.param.BQGReptileInfoParamDTO;
import com.alice.novel.module.common.dto.param.HTSReptileInfoParamDTO;
import com.alice.novel.module.common.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.common.dto.query.NovelInfoQueryDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;

import java.util.List;

/**
 * @Description 小说信息查询
 * @DateTime 2023/11/28 17:40
 */
public interface NovelService {

    /**
     * 添加笔趣阁小说
     *
     * @param reptileInfoParamDTO 要添加的小说信息
     */
    void addNovelByBQG(BQGReptileInfoParamDTO reptileInfoParamDTO);

    /**
     * 添加和图书小说
     *
     * @param reptileInfoParamDTO 要添加的小说信息
     */
    void addNovelByHTS(HTSReptileInfoParamDTO reptileInfoParamDTO);
    /**
     * 查询小说列表
     *
     * @param novelInfoQueryDTO NovelInfoQueryDTO
     * @return List<NovelInfo>
     */
    List<NovelInfo> queryNovelist(NovelInfoQueryDTO novelInfoQueryDTO);

    /**
     * 查询小说章节
     *
     * @param chapterInfoQueryDTO ChapterInfoQueryDTO
     * @return List<NovelChapter>
     */
    List<NovelChapter> queryChapterList(ChapterInfoQueryDTO chapterInfoQueryDTO);

}
