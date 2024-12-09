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

package com.xlf.schedule.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.List;

/**
 * 日程表数据传输对象
 * <p>
 * 该类用于定义日程表数据传输对象;
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    /**
     * 日程主键
     */
    private String scheduleUuid;

    /**
     * 用户主键
     */
    private String userUuid;

    /**
     * 小组主键
     */
    private String groupUuid;

    /**
     * 日程名字
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 开始时间
     */
    private Timestamp startTime;

    /**
     * 结束时间
     */
    private Timestamp endTime;

    /**
     * 类型（0: 单次任务，1: 循环任务，2: 一日任务）
     */
    private Short type;

    /**
     * 循环类型（1: 每天登录，2: 每周, 3: 每个工作日, 4: 每个月 1 号，5: 每个月 14号，0: 自定义）
     */
    private Short loopType;

    /**
     * 自定义天数（多少天循环一次）
     */
    private Integer customLoop;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 优先级(1,2,3,4: 较低，低，中，高）
     */
    private Short priority;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 修改时间
     */
    private Timestamp updatedAt;
}
