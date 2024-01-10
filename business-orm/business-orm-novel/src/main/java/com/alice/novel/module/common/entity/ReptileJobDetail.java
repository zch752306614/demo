package com.alice.novel.module.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_REPTILE_JOB_DETAIL")
public class ReptileJobDetail extends Model<ReptileJobDetail> {

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
