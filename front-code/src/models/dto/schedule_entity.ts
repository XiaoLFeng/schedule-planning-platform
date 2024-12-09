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

/**
 * 日程表数据传输对象
 * <p>
 * 该类用于定义日程表数据传输对象;
 *
 * ScheduleEntity
 */
export type ScheduleEntity = {
    /**
     * 创建时间
     */
    created_at?: number;
    /**
     * 自定义天数（多少天循环一次）
     */
    custom_loop?: number;
    /**
     * 描述
     */
    description?: string;
    /**
     * 结束时间
     */
    end_time?: number;
    /**
     * 小组主键
     */
    group_uuid?: string;
    /**
     * 循环类型（1: 每天登录，2: 每周, 3: 每个工作日, 4: 每个月 1 号，5: 每个月 14号，0: 自定义）
     */
    loop_type?: number;
    /**
     * 日程名字
     */
    name?: string;
    /**
     * 优先级(1,2,3,4: 较低，低，中，高）
     */
    priority?: number;
    /**
     * 日程主键
     */
    schedule_uuid?: string;
    /**
     * 开始时间
     */
    start_time?: number;
    /**
     * 标签
     */
    tags?: string[];
    /**
     * 类型（0: 单次任务，1: 循环任务，2: 一日任务）
     */
    type?: number;
    /**
     * 修改时间
     */
    updated_at?: number;
    /**
     * 用户主键
     */
    user_uuid?: string;
}
