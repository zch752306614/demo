package com.alice.novel.module.common.dao;

import com.alice.novel.module.common.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 小说章节 Mapper 接口
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-11-27
 */
public interface NovelChapterDao extends BaseMapper<NovelChapter> {

    List<NovelChapter> queryChapterList(ChapterInfoQueryDTO chapterInfoQueryDTO);

}
