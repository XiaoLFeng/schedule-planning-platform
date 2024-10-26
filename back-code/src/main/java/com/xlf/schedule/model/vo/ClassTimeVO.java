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
 * 课程时间创建值对象
 * <p>
 * 该类用于定义课程时间创建值对象;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Getter
@SuppressWarnings("unused")
public class ClassTimeVO {
    @NotBlank(message = "课程时间名称不能为空")
    private String name;
    private Boolean isPublic;
    private List<TimeAble> timeAble;

    @Getter
    @SuppressWarnings("unused")
    public static class TimeAble {
        @Pattern(regexp = "^[0-2][0-9]:[0-5][0-9]$", message = "课程时间开始时间格式错误")
        private String startTime;
        @Pattern(regexp = "^[0-2][0-9]:[0-5][0-9]$", message = "课程时间结束时间格式错误")
        private String endTime;
    }
}
