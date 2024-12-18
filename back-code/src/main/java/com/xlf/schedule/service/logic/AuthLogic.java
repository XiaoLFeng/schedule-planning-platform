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

package com.xlf.schedule.service.logic;

import com.xlf.schedule.constant.StringConstant;
import com.xlf.schedule.dao.RoleDAO;
import com.xlf.schedule.dao.UserDAO;
import com.xlf.schedule.model.entity.RoleDO;
import com.xlf.schedule.model.entity.UserDO;
import com.xlf.schedule.model.vo.AuthRegisterVO;
import com.xlf.schedule.service.AuthService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import com.xlf.utility.exception.library.UserAuthenticationException;
import com.xlf.utility.util.PasswordUtil;
import com.xlf.utility.util.UuidUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * 授权逻辑
 * <p>
 * 该类用于定义授权逻辑;
 * 该类使用 {@link Service} 注解标记;
 * 该类实现 {@link AuthService} 接口;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Service
@RequiredArgsConstructor
public class AuthLogic implements AuthService {

    /**
     * 用户数据访问对象
     */
    private final UserDAO userDAO;
    /**
     * 角色数据访问对象
     */
    private final RoleDAO roleDAO;

    @Override
    public void checkUserAndPassword(String userUuid, String password, HttpServletRequest request) {
        UserDO getUserDO = userDAO.lambdaQuery().eq(UserDO::getUuid, userUuid).one();
        if (getUserDO == null) {
            throw new UserAuthenticationException(UserAuthenticationException.ErrorType.USER_NOT_EXIST, request);
        }
        if (!PasswordUtil.verify(password, getUserDO.getPassword())) {
            throw new UserAuthenticationException(UserAuthenticationException.ErrorType.WRONG_PASSWORD, request);
        }
    }

    @Override
    public String registerUser(@NotNull AuthRegisterVO authRegisterVO) {
        // 检查用户信息
        boolean isExistUser = userDAO.lambdaQuery()
                .eq(UserDO::getUsername, authRegisterVO.getUsername()).or()
                .eq(UserDO::getPhone, authRegisterVO.getPhone()).or()
                .eq(UserDO::getEmail, authRegisterVO.getEmail()).exists();
        if (isExistUser) {
            throw new BusinessException("用户已存在", ErrorCode.EXISTED);
        }
        // 注册用户
        String newUserUuid = UuidUtil.generateStringUuid();
        RoleDO getUseRole = roleDAO.lambdaQuery().eq(RoleDO::getName, "USER").one();
        UserDO userDO = new UserDO()
                .setUuid(newUserUuid)
                .setUsername(authRegisterVO.getUsername())
                .setPhone(authRegisterVO.getPhone())
                .setEmail(authRegisterVO.getEmail())
                .setRole(getUseRole.getRoleUuid())
                .setPassword(PasswordUtil.encrypt(authRegisterVO.getPassword()));
        userDAO.save(userDO);
        return newUserUuid;
    }

    @Override
    public void changePassword(String userUuid, String password) {
        UserDO userDO = userDAO.lambdaQuery().eq(UserDO::getUuid, userUuid).one();
        if (userDO == null) {
            throw new BusinessException(StringConstant.USER_NOT_EXIST, ErrorCode.NOT_EXIST);
        }
        userDO
                .setOldPassword(userDO.getPassword())
                .setPassword(PasswordUtil.encrypt(password));
        userDAO.lambdaUpdate().eq(UserDO::getUuid, userUuid).update(userDO);
    }
}
