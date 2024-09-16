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

import com.xlf.schedule.dao.UserDAO;
import com.xlf.schedule.model.entity.UserDO;
import com.xlf.schedule.service.AuthService;
import com.xlf.utility.exception.library.UserAuthenticationException;
import com.xlf.utility.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    public UserDO registerUser() {
        return null;
    }
}
