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

import com.xlf.schedule.constant.SystemConstant;
import com.xlf.schedule.dao.InfoDAO;
import com.xlf.schedule.dao.LogsDAO;
import com.xlf.schedule.dao.RoleDAO;
import com.xlf.schedule.dao.UserDAO;
import com.xlf.schedule.model.entity.InfoDO;
import com.xlf.schedule.model.entity.RoleDO;
import com.xlf.schedule.model.entity.UserDO;
import com.xlf.schedule.model.vo.InitialSetupVO;
import com.xlf.schedule.service.InitialService;
import com.xlf.utility.util.PasswordUtil;
import com.xlf.utility.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 初始化逻辑
 * <p>
 * 该类用于定义初始化逻辑;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Service
@RequiredArgsConstructor
public class InitialLogic implements InitialService {

    /**
     * 用户数据访问对象
     */
    private final UserDAO userDAO;
    private final InfoDAO infoDAO;
    private final RoleDAO roleDAO;
    private final LogsDAO logsDAO;

    @Override
    @Transactional
    public void setUp(@NotNull InitialSetupVO initialSetupVO) {
        RoleDO getAdminRole = roleDAO.lambdaQuery().eq(RoleDO::getName, "ADMIN").one();
        String newUserUuid = UuidUtil.generateStringUuid();
        UserDO newUserDO = new UserDO()
                .setUuid(newUserUuid)
                .setUsername(initialSetupVO.getUsername())
                .setEmail(initialSetupVO.getEmail())
                .setPhone(initialSetupVO.getPhone())
                .setRole(getAdminRole.getRoleUuid())
                .setPassword(PasswordUtil.encrypt(initialSetupVO.getPassword()));
        userDAO.save(newUserDO);
        infoDAO.lambdaUpdate()
                .set(InfoDO::getValue, newUserUuid)
                .eq(InfoDO::getKey, "system_super_admin_uuid")
                .update();
        // 创建测试用户
        RoleDO getUserRole = roleDAO.lambdaQuery().eq(RoleDO::getName, "USER").one();
        String newTestUserUuid = UuidUtil.generateStringUuid();
        UserDO newTestUserDO = new UserDO()
                .setUuid(newTestUserUuid)
                .setUsername("test")
                .setRole(getUserRole.getRoleUuid())
                .setPhone("18888888888")
                .setPassword(PasswordUtil.encrypt("test"));
        if (!SystemConstant.isDebugMode) {
            newTestUserDO.setEnable(false);
        }
        userDAO.save(newTestUserDO);
        infoDAO.lambdaUpdate()
                .set(InfoDO::getValue, newTestUserUuid)
                .eq(InfoDO::getKey, "system_test_user_uuid")
                .update();
        // 修改初始化
        SystemConstant.isInitialMode = "false";
        infoDAO.lambdaUpdate()
                .set(InfoDO::getValue, SystemConstant.isInitialMode)
                .eq(InfoDO::getKey, "system_initial_mode")
                .update();
        // 打日志
        logsDAO.save(0, "INITIAL", null, "设置初始化");
    }
}
