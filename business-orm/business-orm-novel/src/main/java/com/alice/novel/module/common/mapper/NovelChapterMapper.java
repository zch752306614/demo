package com.alice.novel.module.common.mapper;

import com.alice.novel.module.common.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.support.common.base.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小说章节 Mapper 接口
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-11-27
 */
public interface NovelChapterMapper extends MyBaseMapper<NovelChapter> {

    List<NovelChapter> queryChapterList(@Param("chapterInfoQueryDTO") ChapterInfoQueryDTO chapterInfoQueryDTO);

}
