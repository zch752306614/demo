package com.alice.novel.module.common.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Description 路径结构信息
 * @DateTime 2023/11/27 17:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "ReptileInfoParamDTO", description = "路径结构信息")
public class ReptileInfoParamDTO {

    /**
     * 小说名称
     */
    @NotBlank(message = "小说名称不能为空")
    @ApiModelProperty(value = "小说名称", required = true)
    private String novelName;

    /**
     * 小说作者
     */
    @NotBlank(message = "小说作者不能为空")
    @ApiModelProperty(value = "小说作者", required = true)
    private String novelAuthor;

    /**
     * 原始路径
     */
    @NotBlank(message = "原始路径不能为空")
    @ApiModelProperty(value = "原始路径", required = true)
    private String baseUrl;

    /**
     * 路径后缀
     */
    @ApiModelProperty(value = "路径后缀")
    private String urlSuffix;

    /**
     * 起点
     */
    @NotBlank(message = "起点不能为空")
    @ApiModelProperty(value = "起点", required = true)
    private int startIndex;

    /**
     * 终点
     */
    @NotBlank(message = "终点不能为空")
    @ApiModelProperty(value = "终点", required = true)
    private int endIndex;

    /**
     * 间隔
     */
    @ApiModelProperty(value = "间隔")
    private int intervalValue;

    /**
     * 分批标志
     */
    @ApiModelProperty(value = "分批标志")
    private String partFlag;

    /**
     * 分批数量
     */
    @ApiModelProperty(value = "分批数量")
    private int partCount;

    /**
     * 分批后缀
     */
    @ApiModelProperty(value = "分批后缀")
    private String partSuffix;

    /**
     * 分批起点
     */
    @ApiModelProperty(value = "分批起点")
    private int partStartIndex;

    /**
     * 分批间隔
     */
    @ApiModelProperty(value = "分批间隔")
    private int partInterval;

    /**
     * 标题分批分隔符
     */
    @ApiModelProperty(value = "标题分批分隔符")
    private String titleSeparator;

    /**
     * 是否完结
     */
    @ApiModelProperty(value = "是否完结")
    private String completedFlag;

}
