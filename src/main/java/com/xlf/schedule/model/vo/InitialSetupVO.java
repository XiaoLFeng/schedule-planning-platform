package com.xlf.schedule.model.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

/**
 * 初始化设置值对象
 * <p>
 * 该类用于定义初始化设置值对象;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Getter
@SuppressWarnings("unused")
public class InitialSetupVO {
    @Pattern(regexp = "^[a-zA-Z0-9_-]{4,36}$", message = "用户名格式不准确")
    private String username;
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号格式不正确")
    private String phone;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    @NotBlank(message = "密码不能为空")
    private String password;
}
