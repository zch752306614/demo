package com.alice.novel.module.common.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * @Description 笔趣阁路径结构信息
 * @DateTime 2023/11/27 17:58
 */
@Data
@SuperBuilder
@ApiModel(value = "BQGReptileInfoParamDTO", description = "路径结构信息")
public class BQGReptileInfoParamDTO extends ReptileInfoCommonDTO {

    /**
     * 起点
     */
    @NotNull(message = "起点不能为空")
    @ApiModelProperty(value = "起点", required = true)
    private Integer startIndex;

    /**
     * 终点
     */
    @NotNull(message = "终点不能为空")
    @ApiModelProperty(value = "终点", required = true)
    private Integer endIndex;

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
    private Integer partCount;

    /**
     * 分批后缀
     */
    @ApiModelProperty(value = "分批后缀")
    private String partSuffix;

    /**
     * 分批起点
     */
    @ApiModelProperty(value = "分批起点")
    private Integer partStartIndex;

    /**
     * 分批间隔
     */
    @ApiModelProperty(value = "分批间隔")
    private Integer partInterval;

    /**
     * 标题分批分隔符
     */
    @ApiModelProperty(value = "标题分批分隔符")
    private String titleSeparator;

}
