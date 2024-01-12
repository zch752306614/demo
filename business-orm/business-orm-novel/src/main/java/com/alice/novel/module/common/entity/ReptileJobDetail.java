package com.alice.novel.module.common.entity;

import com.alice.support.common.base.entity.MyBaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 *
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-12-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("T_REPTILE_JOB_DETAIL")
public class ReptileJobDetail extends MyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 爬虫任务ID
     */
    @TableField("REPTILE_JOB_ID")
    private Long reptileJobId;

    /**
     * 爬虫任务明细地址
     */
    @TableField("REPTILE_URL")
    private String reptileUrl;

    /**
     * 完成标志
     */
    @TableField("DONE_FLAG")
    private String doneFlag;

    /**
     * 错误信息
     */
    @TableField("ERROR_MSG")
    private String errorMsg;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
