package com.alice.support.common.mybatis.page.one;

import com.alice.support.common.annotation.Length;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description 分页参数
 * @DateTime 2023/11/28 9:58
 */
@ApiModel(value = "分页参数")
@Data
@Accessors(chain = true)
public class PageParam implements Serializable {

    @ApiModelProperty(value = "页码，从 1 开始", required = true, example = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数，最大值为 1000", required = true, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Length(min = 1, max = 1000, message = "条数范围为[1, 1000]")
    private Integer pageSize;

    @ApiModelProperty(value = "排序字段,以及排序方式")
    private List<Map<String, Sort>> sortBy;

    @ApiModelProperty(hidden = true)
    public final int getOffset() {
        return null == pageNum ? 0 : (pageNum - 1) * pageSize;
    }

    // 这里的@Getter不要漏写哦
    @Getter
    public enum Sort {
        ASC, DESC
    }
}
