package com.alice.novel.module.common.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 查询小说入参
 * @DateTime 2023/11/28 17:42
 */
@Data
@ApiModel(value = "NovelInfoQueryDTO", description = "查询小说入参")
public class NovelInfoQueryDTO {

    @ApiModelProperty(value = "小说名称")
    private String novelName;

    @ApiModelProperty(value = "小说作者")
    private String novelAuthor;

}
