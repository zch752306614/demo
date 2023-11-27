package com.alice.novel.module.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@TableName("T_NOVEL_CHAPTER")
public class NovelChapter extends Model<NovelChapter> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    private Integer id;

    /**
     * 小说编号
     */
    @TableField("NOVEL_ID")
    private Integer novelId;

    /**
     * 小说章节
     */
    @TableField("CHAPTE_NUMBER")
    private Integer chapteNumber;

    /**
     * 小说章节名
     */
    @TableField("CHAPTE_NAME")
    private String chapteName;

    /**
     * 小说正文
     */
    @TableField("CHAPTER_COMTENT")
    private String chapterComtent;

    /**
     * 正文分部
     */
    @TableField("CHAPTER_PART")
    private Integer chapterPart;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
