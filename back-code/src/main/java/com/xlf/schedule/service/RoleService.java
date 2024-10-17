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

package com.xlf.schedule.service;

/**
 * 角色服务
 * <p>
 * 该接口用于定义角色服务;
 * 该接口使用 public 修饰;
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
public interface RoleService {

    /**
     * 检查角色是否有管理员
     * <p>
     * 该方法用于检查角色是否有管理员；
     * 如果有管理员，则抛出异常。
     *
     * @param roleUuid 角色UUID
     */
    boolean checkRoleHasAdminByUuid(String roleUuid);
}
