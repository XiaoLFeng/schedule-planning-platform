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
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

/**
 * 日程添加值对象
 * <p>
 * 该类用于定义日程添加值对象;
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Getter
@SuppressWarnings("unused")
public class ScheduleAddVO {
    @NotNull(message = "添加位置不能为空")
    private Boolean addLocation;
    private String groupUuid;
    @NotBlank(message = "日程名称不能为空")
    private String name;
    private String description;
    @NotBlank(message = "开始时间不能为空")
    private Timestamp startTime;
    @NotBlank(message = "结束时间不能为空")
    private Timestamp endTime;
    @NotNull(message = "日程类型不能为空")
    private Short type;
    @NotNull(message = "循环类型不能为空")
    private Short loopType;
    @NotNull(message = "循环间隔不能为空")
    private Integer customLoop;
    private List<String> tags;
    @NotNull(message = "优先级不能为空")
    private Short priority;
    private List<String> resources;
}
