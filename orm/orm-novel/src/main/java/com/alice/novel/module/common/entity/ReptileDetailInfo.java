package com.alice.novel.module.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 爬虫任务明细信息
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_REPTILE_DETAIL_INFO")
public class ReptileDetailInfo extends Model<ReptileDetailInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 爬虫任务主键
     */
    @TableField("REPTILE_INFO_ID")
    private Integer reptileInfoId;

    /**
     * 爬虫任务明细地址
     */
    @TableField("REPTILE_URL")
    private String reptileUrl;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
