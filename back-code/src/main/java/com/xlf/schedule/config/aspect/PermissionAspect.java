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

package com.xlf.schedule.config.aspect;

import com.xlf.schedule.annotations.CheckAccessToYourOwnUuidOrAdminUuid;
import com.xlf.schedule.constant.PatternConstant;
import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.service.RoleService;
import com.xlf.schedule.service.UserService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.library.ServerInternalErrorException;
import com.xlf.utility.exception.library.UserAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 权限切面
 * <p>
 * 该类用于定义权限切面;
 * 该类使用 {@link Aspect} 注解标记;
 * 该类使用 {@link Component} 注解标记;
 * 该类使用 {@link RequiredArgsConstructor} 注解标记;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {
    private final UserService userService;
    private final RoleService roleService;

    /**
     * 检查权限
     * <p>
     * 该方法用于检查权限；用于检查用户是否有权限访问；
     * 即检查用户是否登录。
     * 该方法使用 {@link com.xlf.utility.annotations.HasAuthorize} 注解标记；
     */
    @Before("@annotation(com.xlf.utility.annotations.HasAuthorize)")
    public void checkPermission() {
        // 截获 HttpServletRequest 对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();

            // 获取请求头中的令牌
            UserDTO getUser = userService.getUserByToken(request);
            if (getUser == null) {
                throw new UserAuthenticationException(UserAuthenticationException.ErrorType.USER_NOT_LOGIN, request);
            }
        } else {
            throw new ServerInternalErrorException("无法获取请求对象");
        }
    }

    /**
     * 检查访问自己的UUID
     * <p>
     * 该方法用于检查访问自己的UUID；用于检查用户是否有权限访问自己的UUID；
     * 即检查用户是否登录。
     * 该方法使用 {@link CheckAccessToYourOwnUuidOrAdminUuid} 注解标记；
     */
    @Around("@annotation(com.xlf.schedule.annotations.CheckAccessToYourOwnUuidOrAdminUuid) && args(userUuid,..)")
    public Object checkAccessToYourOwnUuid(@NotNull ProceedingJoinPoint pjp, String userUuid) throws Throwable {
        Object[] args = pjp.getArgs();
        // 截获 HttpServletRequest 对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();

            if (!Pattern.matches(PatternConstant.UUID, userUuid)) {
                throw new IllegalDataException(ErrorCode.PARAMETER_ILLEGAL);
            }
            // 验证用户是否有权限访问
            UserDTO getUser = userService.getUserByToken(request);
            if (!getUser.getUuid().equals(userUuid)) {
                // 检查是否是管理员
                if (!roleService.checkRoleHasAdminByUuid(getUser.getRole())) {
                    throw new UserAuthenticationException(UserAuthenticationException.ErrorType.PERMISSION_DENIED, request);
                }
                Arrays.stream(args).filter(arg -> arg instanceof Boolean).forEach(arg -> args[Arrays.asList(args).indexOf(arg)] = true);
            } else {
                Arrays.stream(args).filter(arg -> arg instanceof Boolean).forEach(arg -> args[Arrays.asList(args).indexOf(arg)] = false);
            }
        } else {
            throw new ServerInternalErrorException("无法获取请求对象");
        }
        return pjp.proceed();
    }
}
