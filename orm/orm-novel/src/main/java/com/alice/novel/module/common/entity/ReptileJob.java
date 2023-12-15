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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_REPTILE_JOB")
public class ReptileJob extends Model<ReptileJob> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 小说名称
     */
    @TableField("NOVEL_NAME")
    private String novelName;

    /**
     * 小说作者
     */
    @TableField("NOVEL_AUTHOR")
    private String novelAuthor;

    /**
     * 是否完结
     */
    @TableField("COMPLETED_FLAG")
    private String completedFlag;

    /**
     * 原始路径
     */
    @TableField("BASE_URL")
    private String baseUrl;

    /**
     * 小说编号
     */
    @TableField("NOVEL_NUMBER")
    private String novelNumber;

    /**
     * 路径后缀
     */
    @TableField("URL_SUFFIX")
    private String urlSuffix;

    /**
     * 完成标志
     */
    @TableField("DONE_FLAG")
    private String doneFlag;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
