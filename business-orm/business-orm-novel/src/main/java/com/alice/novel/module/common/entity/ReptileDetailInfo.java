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
 * 爬虫任务明细信息
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-11-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("T_REPTILE_DETAIL_INFO")
public class ReptileDetailInfo extends MyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 爬虫任务主键
     */
    @TableField("REPTILE_INFO_ID")
    private Long reptileInfoId;

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
