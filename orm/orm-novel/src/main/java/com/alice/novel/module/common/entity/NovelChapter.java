package com.alice.novel.module.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 小说章节
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("T_NOVEL_CHAPTER")
public class NovelChapter extends Model<NovelChapter> {

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
     * 小说章节
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
     * 正文分部
     */
    @TableField("CHAPTER_PART")
    private Integer chapterPart;

    /**
     * 正文分部
     */
    @TableField("CHAPTER_WORDS_COUNT")
    private Integer chapterWordsCount;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
