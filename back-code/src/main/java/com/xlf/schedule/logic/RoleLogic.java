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

package com.xlf.schedule.logic;

import com.xlf.schedule.dao.RoleDAO;
import com.xlf.schedule.model.entity.RoleDO;
import com.xlf.schedule.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 角色逻辑
 * <p>
 * 该类用于定义角色逻辑;
 * 该类使用 {@link Service} 注解标记;
 * 该类实现 {@link RoleService} 接口;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Service
@RequiredArgsConstructor
public class RoleLogic implements RoleService {
    private final RoleDAO roleDAO;

    @Override
    public boolean checkRoleHasAdminByUuid(String roleUuid) {
        RoleDO getRole = roleDAO.lambdaQuery()
                .eq(RoleDO::getRoleUuid, roleUuid)
                .one();
        return "ADMIN".equals(getRole.getName());
    }
}
