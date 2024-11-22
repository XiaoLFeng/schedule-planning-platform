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

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

/**
 * 用户编辑值对象
 * <p>
 * 该类用于定义用户编辑值对象；
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Getter
@SuppressWarnings("unused")
public class UserEditVO {
    @Pattern(regexp = "^(|[a-zA-Z0-9_-]{4,36})$", message = "用户名格式错误")
    private String username;
    @Pattern(regexp = "^(|(13\\d|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18\\d|19[0-35-9])\\d{8})$", message = "手机号格式不正确")
    private String phone;
    @Pattern(regexp = "^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$", message = "邮箱格式不正确")
    private String email;
    @Pattern(regexp = "^(|[0-9A-Za-z]{6})$", message = "邮箱验证码格式不正确")
    private String emailCode;
}
