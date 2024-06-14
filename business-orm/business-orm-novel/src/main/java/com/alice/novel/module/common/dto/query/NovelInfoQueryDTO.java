package com.alice.novel.module.common.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

    @ApiModelProperty(value = "搜索关键字")
    private String searchName;

    @ApiModelProperty(value = "小说的ID编号列表")
    private List<String> idList;

}
