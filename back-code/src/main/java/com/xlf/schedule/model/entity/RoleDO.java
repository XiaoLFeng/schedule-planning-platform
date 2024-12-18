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
 * 用户角色表实体
 * <p>
 * 该类用于定义用户角色表实体;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Data
@TableName("xf_role")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDO {
    /**
     * 用户角色表主键
     */
    @TableId(type = IdType.NONE)
    private String roleUuid;
    /**
     * 用户角色表名称
     */
    private String name;
    /**
     * 用户角色表显示名称
     */
    private String displayName;
    /**
     * 用户角色表描述
     */
    private String roleDesc;
    /**
     * 用户角色表创建时间
     */
    private Timestamp createdAt;
}
