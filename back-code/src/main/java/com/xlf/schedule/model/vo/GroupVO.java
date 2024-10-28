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
import lombok.Getter;

import java.util.List;

/**
 * 创建小组VO
 * <p>
 * 该类是创建小组VO类，用于接收创建小组请求参数
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Getter
@SuppressWarnings("unused")
public class GroupVO {
    @NotBlank(message = "小组名字不能为空")
    private String name;
    private List<String> tags;
    private Boolean ableAdd;
}
