package com.alice.novel.module.common.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @Description 爬虫通用入参
 * @DateTime 2023/12/11 13:04
 */
@Data
@SuperBuilder
@ApiModel(value = "ReptileInfoCommonDTO", description = "爬虫通用入参")
public class ReptileInfoCommonDTO {

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
