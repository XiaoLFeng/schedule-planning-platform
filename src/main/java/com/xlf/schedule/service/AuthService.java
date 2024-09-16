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

import com.xlf.schedule.model.vo.AuthRegisterVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 授权服务接口
 * <br/>
 * 授权服务，用于授权相关操作。
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
public interface AuthService {

    /**
     * 检查用户和密码
     * <p>
     * 该方法用于检查用户和密码；首先检查用户是否存在，然后检查密码是否正确；
     * 用户可查询条件有三种 {@code 手机号}、{@code 邮箱}、{@code 用户名}；
     * 查询成功用户后，进行密码匹配，匹配成功则返回用户信息，否则抛出异常。
     *
     * @param userUuid 用户UUID
     * @param password 密码
     * @param request 请求
     */
    void checkUserAndPassword(String userUuid, String password, HttpServletRequest request);

    /**
     * 注册用户
     * <p>
     * 该方法用于注册用户；注册用户时，需要提供 {@code 用户名}、{@code 手机号}、{@code 邮箱}、{@code 密码}；
     * 注册成功后，返回用户信息。
     *
     * @param authRegisterVO 授权注册值对象
     * @return 用户UUID
     */
    String registerUser(AuthRegisterVO authRegisterVO);
}
