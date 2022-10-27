package com.alice.zhang.support.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Alice
 * @version 1.0
 * @date 2020/8/8 8:58
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 199979165926355412L;

    @TableId("user_id")
    int userId;

    @TableField("user_name")
    String userName;

    @TableField("user_password")
    String userPassword;

    @TableField("user_photo")
    String userPhoto;

    @TableField("user_age")
    String userAge;

}
