package com.aigc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
@Builder
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 账户
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户密码密文
     */
    @TableField(value = "password")
    private String password;

    /**
     * 手机号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 真实名称
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 账户状态(1.正常 2.锁定 )
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 性别(1.男 2.女)
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 是否删除(1未删除；0已删除)
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建来源(1.web 2.android 3.ios )
     */
    @TableField(value = "create_where")
    private Integer createWhere;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}