package com.alice.novel.module.novel.service;

import com.alice.novel.module.common.dto.param.ReptileInfoParamDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.common.dto.query.NovelInfoQueryDTO;

import java.util.List;

/**
 * @Description 小说信息查询
 * @DateTime 2023/11/28 17:40
 */
public interface NovelService {

    /**
     * 添加小说
     *
     * @param reptileInfoParamDTO 要添加的小说信息
     */
    void addNovel(ReptileInfoParamDTO reptileInfoParamDTO);

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
