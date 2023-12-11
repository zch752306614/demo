package com.alice.novel.module.common.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * @Description 笔趣阁路径结构信息
 * @DateTime 2023/11/27 17:58
 */
@Data
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "BQGReptileInfoParamDTO", description = "路径结构信息")
public class BQGReptileInfoParamDTO extends ReptileInfoCommonDTO {



}
