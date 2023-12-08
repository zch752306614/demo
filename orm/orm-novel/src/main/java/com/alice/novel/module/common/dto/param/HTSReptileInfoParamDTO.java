package com.alice.novel.module.common.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Description 和图书路径结构信息
 * @DateTime 2023/12/8 12:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "BQGReptileInfoParamDTO", description = "路径结构信息")
public class HTSReptileInfoParamDTO {

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
     * 原始路径
     */
    @NotBlank(message = "小说编号")
    @ApiModelProperty(value = "小说编号", required = true)
    private String novelNumber;

    /**
     * 路径后缀
     */
    @ApiModelProperty(value = "路径后缀")
    private String urlSuffix;

    /**
     * 是否完结
     */
    @ApiModelProperty(value = "是否完结")
    private String completedFlag;

}
