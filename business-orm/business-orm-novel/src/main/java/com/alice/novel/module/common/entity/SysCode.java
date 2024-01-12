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
 * 系统编码表
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-11-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("T_SYS_CODE")
public class SysCode extends MyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 编码
     */
    @TableField("CODE")
    private String code;

    /**
     * 编码值
     */
    @TableField("CODE_VALUE")
    private String codeValue;

    /**
     * 编码名称
     */
    @TableField("CODE_NAME")
    private String codeName;

    /**
     * 编码排序
     */
    @TableField("CODE_ORDER")
    private Integer codeOrder;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
