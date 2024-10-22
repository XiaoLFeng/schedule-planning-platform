/*
 * ***************************************************************************************
 * author: XiaoLFeng(https://www.x-lf.com)
 * about:
 *   The project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * licenseStatement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *     in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *     caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *     you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

package com.xlf.schedule.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 用户表实体
 * <p>
 * 该类用于定义用户表实体;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Data
@TableName("xf_user")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {
    /**
     * 用户表主键
     */
    @TableId(type = IdType.NONE)
    private String uuid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户邮箱校验
     */
    private Boolean emailVerify;
    /**
     * 用户当前密码
     */
    private String password;
    /**
     * 用户旧密码
     */
    private String oldPassword;
    /**
     * 角色
     */
    private String role;
    /**
     * 创建时间
     */
    private Timestamp createdAt;
    /**
     * 修改时间
     */
    private Timestamp updatedAt;
    /**
     * 用户是否开启
     */
    private Boolean enable;
    /**
     * 封禁到
     */
    private Timestamp bannedAt;
    /**
     * 封禁原因
     */
    private String banReason;
}
