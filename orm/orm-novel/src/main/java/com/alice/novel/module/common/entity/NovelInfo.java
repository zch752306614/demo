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
 * 小说清单
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
@TableName("T_NOVEL_INFO")
public class NovelInfo extends Model<NovelInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 小说名称
     */
    @TableField("NOVEL_NAME")
    private String novelName;

    /**
     * 作者
     */
    @TableField("NOVEL_AUTHOR")
    private String novelAuthor;

    /**
     * 封面图片URL
     */
    @TableField("NOVEL_COVER")
    private String novelCover;

    /**
     * 章节数
     */
    @TableField("NOVEL_CHAPTER_COUNT")
    private Integer novelChapterCount;

    /**
     * 字数
     */
    @TableField("NOVEL_WORDS_COUNT")
    private Integer novelWordsCount;

    /**
     * 小说类型
     */
    @TableField("NOVEL_TYPE")
    private String novelType;

    /**
     * 发行时间
     */
    @TableField("NOVEL_ISSUE_DATE")
    private String novelIssueDate;

    /**
     * 最后更新时间
     */
    @TableField("LAST_UPDATE_TIME")
    private String lastUpdateTime;

    /**
     * 是否完结
     */
    @TableField("COMPLETED_FLAG")
    private String completedFlag;

    /**
     * 小说简介
     */
    @TableField("NOVEL_INTRODUCTION")
    private String novelIntroduction;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
