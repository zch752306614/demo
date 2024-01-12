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
 * 爬虫任务信息
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-11-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("T_REPTILE_INFO")
public class ReptileInfo extends MyBaseEntity {

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
     * 小说作者
     */
    @TableField("NOVEL_AUTHOR")
    private String novelAuthor;

    /**
     * 是否完结
     */
    @TableField("COMPLETED_FLAG")
    private String completedFlag;

    /**
     * 原始路径
     */
    @TableField("BASE_URL")
    private String baseUrl;

    /**
     * 路径后缀
     */
    @TableField("URL_SUFFIX")
    private String urlSuffix;

    /**
     * 起点
     */
    @TableField("START_INDEX")
    private Integer startIndex;

    /**
     * 终点
     */
    @TableField("END_INDEX")
    private Integer endIndex;

    /**
     * 间隔
     */
    @TableField("INTERVAL_VALUE")
    private Integer intervalValue;

    /**
     * 分批标志
     */
    @TableField("PART_FLAG")
    private String partFlag;

    /**
     * 分批数量
     */
    @TableField("PART_COUNT")
    private Integer partCount;

    /**
     * 分批后缀
     */
    @TableField("PART_SUFFIX")
    private String partSuffix;

    /**
     * 分批起点
     */
    @TableField("PART_START_INDEX")
    private Integer partStartIndex;

    /**
     * 分批间隔
     */
    @TableField("PART_INTERVAL")
    private Integer partInterval;

    /**
     * 标题分批分隔符
     */
    @TableField("TITLE_SEPARATOR")
    private String titleSeparator;

    /**
     * 是否完成
     */
    @TableField("DONE_FLAG")
    private String doneFlag;

    /**
     * 中止位置
     */
    @TableField("PAUSE_INDEX")
    private Integer pauseIndex;

    /**
     * 任务开始时间
     */
    @TableField("START_TIME")
    private String startTime;

    /**
     * 任务结束时间
     */
    @TableField("END_TIME")
    private String endTime;

    /**
     * 是否有效
     */
    @TableField("VALUE_FLAG")
    private String valueFlag;

    /**
     * 错误信息
     */
    @TableField("ERROR_MSG")
    private String errorMsg;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
