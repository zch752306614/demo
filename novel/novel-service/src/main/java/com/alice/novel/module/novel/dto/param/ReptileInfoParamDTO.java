package com.alice.novel.module.novel.dto.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 路径结构信息
 * @DateTime 2023/11/27 17:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReptileInfoParamDTO {

    /**
     * 小说名称
     */
    private String novelName;

    /**
     * 小说作者
     */
    private String novelAuthor;

    /**
     * 原始路径
     */
    private String baseUrl;

    /**
     * 路径后缀
     */
    private String urlSuffix;

    /**
     * 起点
     */
    private int startIndex;

    /**
     * 终点
     */
    private int endIndex;

    /**
     * 间隔
     */
    private int interval;

    /**
     * 分批标志
     */
    private boolean partFlag;

    /**
     * 分批数量
     */
    private int partCount;

    /**
     * 分批后缀
     */
    private String partSuffix;

    /**
     * 分批起点
     */
    private int partStartIndex;

    /**
     * 分批间隔
     */
    private int partInterval;

    /**
     * 标题分批分隔符
     */
    private String titleSeparator;

}
