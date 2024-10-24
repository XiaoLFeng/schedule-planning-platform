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

package com.xlf.schedule.model.vo;

import lombok.Getter;

/**
 * 授权重置密码视图对象
 * <p>
 * 该类用于定义授权重置密码视图对象；
 * 该类使用 {@link Getter} 注解标记；
 * 该类包含 {@code 邮箱}、{@code 验证码}、{@code 密码} 三个属性；
 * 该类用于接收授权重置密码请求参数。
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Getter
@SuppressWarnings("unused")
public class AuthResetPasswordVO {
    private String mail;
    private String code;
    private String password;
}
