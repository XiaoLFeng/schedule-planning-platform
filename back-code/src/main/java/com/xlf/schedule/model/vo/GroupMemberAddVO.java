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

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

/**
 * 小组成员添加请求参数
 * <p>
 * 该类是小组成员添加请求参数类，用于接收小组成员添加请求参数
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Getter
@SuppressWarnings("unused")
public class GroupMemberAddVO {
    @NotBlank(message = "小组标识符不能为空")
    @Pattern(regexp = "^[0-9a-f]{32}$", message = "小组标识符格式错误")
    private String groupUuid;
    @NotBlank(message = "用户标识符不能为空")
    @Pattern(regexp = "^[0-9a-f]{32}$", message = "用户标识符格式错误")
    private List<String> userUuid;
}
