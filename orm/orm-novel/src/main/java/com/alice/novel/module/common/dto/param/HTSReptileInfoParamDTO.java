package com.alice.novel.module.common.dto.param;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @Description 和图书路径结构信息
 * @DateTime 2023/12/8 12:38
 */
@Data
@SuperBuilder
@ApiModel(value = "BQGReptileInfoParamDTO", description = "路径结构信息")
public class HTSReptileInfoParamDTO extends ReptileInfoCommonDTO {


}
