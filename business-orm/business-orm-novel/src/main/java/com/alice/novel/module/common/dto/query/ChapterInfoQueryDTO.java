package com.alice.novel.module.common.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description 查询小说章节入参
 * @DateTime 2023/11/28 18:22
 */
@Data
@ApiModel(value = "ChapterInfoQueryDTO", description = "查询小说章节信息入参")
public class ChapterInfoQueryDTO {

    @ApiModelProperty(value = "小说章节ID")
    private Long id;

    @NotNull(message = "小说ID不能为空")
    @ApiModelProperty(value = "小说ID")
    private Long novelInfoId;

    @ApiModelProperty(value = "是否获取正文标识")
    private String contentFlag;

    @ApiModelProperty(value = "小说章节ID列表")
    private List<Long> idList;

    @ApiModelProperty(value = "小说章节编号列表")
    private List<Long> chapterNumberList;

}
