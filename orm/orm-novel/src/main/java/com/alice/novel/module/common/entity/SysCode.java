package com.alice.novel.module.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统编码表
 * </p>
 *
 * @author ZhangChenhuang
 * @since 2023-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("T_SYS_CODE")
public class SysCode extends Model<SysCode> {

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
