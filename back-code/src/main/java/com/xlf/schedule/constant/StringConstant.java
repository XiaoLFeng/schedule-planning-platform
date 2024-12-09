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

package com.xlf.schedule.constant;

/**
 * 字符串常量
 * <p>
 * 该类用于定义字符串常量;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
public class StringConstant {
    public static final String UNABLE_GET_REQUEST_OBJECT = "无法获取请求对象";
    public static final String SYSTEM_DEFAULT_CLASS_TIME_UUID = "system_default_class_time_uuid";
    public static final String DATE_TIMER = "yyyy-MM-dd";
    public static final String CLASS_SCHEDULES_ILLEGAL = "课程表UUID非法";
    public static final String USER_FORMAT_INCORRECT = "用户主键有误";
    public static final String CLASS_SCHEDULES_TIME_MARKET = "课程时间市场UUID非法";
    public static final String CLASS_TIME_START_TIME = "开始节数非法";
    public static final String CLASS_TIME_END_TIME = "结束节数非法";
    public static final String SEARCH_CONDITION_ILLEGAL = "搜索内容不合法";
    public static final String GROUP_NOT_EXIST = "小组不存在";
    public static final String NO_PERMISSION_DELETE = "您没有权限删除";
    public static final String NO_PERMISSION_UPDATE = "您没有权限编辑";
    public static final String NO_PERMISSION_QUERY = "您没有权限查看";
    public static final String NOT_GROUP_MEMBER = "您不是该小组成员";
    public static final String USER_NOT_EXIST = "用户不存在";
    public static final String COURSE_TIME_NOT_EXIST = "课程时间不存在";
    public static final String CLASS_SCHEDULE_NOT_EXIST = "课程表不存在";
    public static final String SCHEDULE_UUID_ILLEGAL = "日程标识符有误";
    public static final String SCHEDULE_NOT_EXIST = "日程不存在";

    private StringConstant() {
        throw new IllegalStateException("Utility class");
    }
}
