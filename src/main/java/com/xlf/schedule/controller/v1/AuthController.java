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

package com.xlf.schedule.controller.v1;

import com.xlf.schedule.model.dto.AuthUserDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.vo.AuthChangePasswordVO;
import com.xlf.schedule.model.vo.AuthLoginVO;
import com.xlf.schedule.model.vo.AuthRegisterVO;
import com.xlf.schedule.model.vo.AuthResetPasswordVO;
import com.xlf.schedule.service.AuthService;
import com.xlf.schedule.service.MailService;
import com.xlf.schedule.service.TokenService;
import com.xlf.schedule.service.UserService;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * 授权控制器
 * <p>
 * 该类用于定义授权控制器;
 * 该类使用 {@link RestController} 注解标记;
 * 该类使用 {@link RequestMapping} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final TokenService tokenService;
    private final MailService mailService;

    /**
     * 登录
     * <p>
     * 该方法用于登录。
     *
     * @return {@link AuthLoginVO} 登录信息
     */
    @PostMapping
    @Transactional
    public ResponseEntity<BaseResponse<AuthUserDTO>> login(
            @Validated @RequestBody AuthLoginVO authLoginVO,
            HttpServletRequest request
    ) {
        // 检查用户名输入是否有效
        if (Pattern.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", authLoginVO.getUser())
                || Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", authLoginVO.getUser())
                || Pattern.matches("^[a-zA-Z0-9_-]{4,36}$", authLoginVO.getUser())
        ) {
            UserDTO getUserDTO = userService.getUserForThreeType(authLoginVO.getUser());
            authService.checkUserAndPassword(getUserDTO.getUuid(), authLoginVO.getPassword(), request);
            String getUserToken = tokenService.createToken(getUserDTO.getUuid(), 12L, request);
            AuthUserDTO authUserDTO = new AuthUserDTO()
                    .setUser(getUserDTO)
                    .setToken(getUserToken);
            return ResultUtil.success("登录成功", authUserDTO);
        } else {
            throw new BusinessException("用户名格式不正确", ErrorCode.BODY_ERROR);
        }
    }

    /**
     * 注册
     * <p>
     * 该方法用于注册。
     *
     * @return {@link AuthRegisterVO} 注册信息
     */
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<BaseResponse<AuthUserDTO>> register(
            @Validated @RequestBody AuthRegisterVO authRegisterVO,
            HttpServletRequest request
    ) {
        String getUserUuid = authService.registerUser(authRegisterVO);
        UserDTO getUserDTO = userService.getUserByUuid(getUserUuid);
        String getUserToken = tokenService.createToken(getUserUuid, 12L, request);
        AuthUserDTO authUserDTO = new AuthUserDTO()
                .setUser(getUserDTO)
                .setToken(getUserToken);
        return ResultUtil.success("注册成功", authUserDTO);
    }

    /**
     * 登出
     * <p>
     * 该方法用于登出。
     *
     * @return {@link Void} 空
     */
    @GetMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(HttpServletRequest request) {
        tokenService.deleteTokenByRequest(request);
        return ResultUtil.success("登出成功");
    }

    /**
     * 修改密码
     * <p>
     * 该方法用于修改密码。
     *
     * @return {@link Void} 空
     */
    @PutMapping("/password/change")
    public ResponseEntity<BaseResponse<Void>> changePassword(
            @Validated @RequestBody AuthChangePasswordVO authChangePasswordVO,
            HttpServletRequest request
    ) {
        UserDTO getUserDTO = userService.getUserByToken(request);
        authService.checkUserAndPassword(getUserDTO.getUuid(), authChangePasswordVO.getOldPassword(), request);
        authService.changePassword(getUserDTO.getUuid(), authChangePasswordVO.getNewPassword());
        return ResultUtil.success("修改密码成功");
    }

    /**
     * 重置密码
     * <p>
     * 该方法用于重置密码。
     *
     * @return {@link Void} 空
     */
    @PutMapping("/password/reset")
    @Transactional
    public ResponseEntity<BaseResponse<Void>> resetPassword(
            @Validated @RequestBody AuthResetPasswordVO authResetPasswordVO
    ) {
        UserDTO getUserDTO = userService.getUserByEmail(authResetPasswordVO.getMail());
        if (mailService.verifyMailCode(authResetPasswordVO.getMail(), authResetPasswordVO.getCode())) {
            authService.changePassword(getUserDTO.getUuid(), authResetPasswordVO.getPassword());
        } else {
            throw new BusinessException("验证码错误", ErrorCode.OPERATION_DENIED);
        }
        return ResultUtil.success("重置密码成功");
    }
}
