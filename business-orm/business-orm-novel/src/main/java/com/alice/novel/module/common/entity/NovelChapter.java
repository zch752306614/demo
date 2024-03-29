package com.alice.novel.module.common.entity;

import com.alice.support.common.base.entity.MyBaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 小说章节
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-11-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("T_NOVEL_CHAPTER")
public class NovelChapter extends MyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 小说编号
     */
    @TableField("NOVEL_INFO_ID")
    private Long novelInfoId;

    /**
     * 小说章节编号
     */
    @TableField("CHAPTER_NUMBER")
    private Integer chapterNumber;

    /**
     * 小说章节名
     */
    @TableField("CHAPTER_NAME")
    private String chapterName;

    /**
     * 小说正文
     */
    @TableField("CHAPTER_CONTENT")
    private String chapterContent;

    /**
     * 小说章节卷名
     */
    @TableField("CHAPTER_PART")
    private String chapterPart;

    /**
     * 章节字数
     */
    @TableField("CHAPTER_WORDS_COUNT")
    private Integer chapterWordsCount;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
