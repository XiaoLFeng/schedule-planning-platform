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

import {Calendar, type CalendarProps} from "antd";
import dayjs, {Dayjs} from "dayjs";
import React, {JSX, useEffect, useState} from "react";
import {RomanI, RomanII, RomanIII, RomanIV} from "../../../assets/icon/roman_numerals_svg.tsx";
import {ScheduleEntity} from "../../../models/dto/schedule_entity.ts";
import {ScheduleGetGroupDTO} from "../../../models/dto/schedule_get_group_dto.ts";

/**
 * # 看板视图年月
 * 用于展示看板视图年月相关的信息
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
export function DashboardViewYearAndMonth({getScheduleData, searchInfo, emit}: Readonly<{
    getScheduleData: ScheduleEntity[],
    searchInfo: ScheduleGetGroupDTO
    emit: ({group_uuid, start_time, end_time}: ScheduleGetGroupDTO) => void
}>) {
    const [scheduleData, setScheduleData] = useState<ScheduleEntity[]>([] as ScheduleEntity[]);

    useEffect(() => {
        setScheduleData(getScheduleData);
        console.log("getScheduleData", getScheduleData);
    }, [getScheduleData]);

    const getListData = (value: Dayjs) => {
        const listData: { content: React.ReactElement | string }[] = []; // Specify the type of listData
        if (scheduleData.length > 0) {
            scheduleData.forEach(schedule => {
                // 对获取的数据进行判断月日，然后填入数据
                if (value.year() === dayjs(schedule.start_time).year()) {
                    if (value.month() === dayjs(schedule.start_time).month()) {
                        if (value.date() === dayjs(schedule.start_time).date()) {
                            let priorityElement: JSX.Element;
                            switch (schedule.priority) {
                                case 4: {
                                    priorityElement = (
                                        <div
                                            className={"px-1 bg-red-400 fill-red-900 rounded text-white flex items-center"}>
                                            <RomanI/>
                                        </div>
                                    );
                                    break;
                                }
                                case 3: {
                                    priorityElement = (
                                        <div
                                            className={"px-1 bg-yellow-400 fill-yellow-900 rounded text-white flex items-center"}>
                                            <RomanII/>
                                        </div>
                                    );
                                    break;
                                }
                                case 2: {
                                    priorityElement = (
                                        <div
                                            className={"px-1 bg-green-400 fill-green-900 rounded text-white flex items-center"}>
                                            <RomanIII/>
                                        </div>
                                    );
                                    break;
                                }
                                case 1: {
                                    priorityElement = (
                                        <div
                                            className={"px-1 bg-teal-400 fill-teal-900 rounded text-white flex items-center"}>
                                            <RomanIV/>
                                        </div>
                                    );
                                    break;
                                }
                                default: {
                                    priorityElement = (
                                        <div
                                            className={"px-1 bg-teal-400 fill-teal-900 rounded text-white flex items-center"}>
                                            <RomanIV/>
                                        </div>
                                    );
                                    break;
                                }
                            }
                            listData.push({
                                content: (
                                    <div className={"flex gap-1 text-sm whitespace-nowrap"}>
                                        {priorityElement}
                                        <div className={"px-1 bg-teal-500 rounded text-white"}>日程</div>
                                        <div>{schedule.name}</div>
                                    </div>
                                )
                            });
                        }
                    }
                }
            });
        }
        return listData || [];
    };

    const getMonthData = (value: Dayjs) => {
        const listData: { content: React.ReactElement | string }[] = []; // Specify the type of listData
        scheduleData.forEach(schedule => {
            // 对获取的数据进行判断月日，然后填入数据
            if (value.year() === dayjs(schedule.start_time).year()) {
                if (value.month() === dayjs(schedule.start_time).month()) {
                    let priorityElement: JSX.Element;
                    switch (schedule.priority) {
                        case 4: {
                            priorityElement = (
                                <div
                                    className={"px-1 bg-red-400 fill-red-900 rounded text-white flex items-center"}>
                                    <RomanI/>
                                </div>
                            );
                            break;
                        }
                        case 3: {
                            priorityElement = (
                                <div
                                    className={"px-1 bg-yellow-400 fill-yellow-900 rounded text-white flex items-center"}>
                                    <RomanII/>
                                </div>
                            );
                            break;
                        }
                        case 2: {
                            priorityElement = (
                                <div
                                    className={"px-1 bg-green-400 fill-green-900 rounded text-white flex items-center"}>
                                    <RomanIII/>
                                </div>
                            );
                            break;
                        }
                        case 1: {
                            priorityElement = (
                                <div
                                    className={"px-1 bg-teal-400 fill-teal-900 rounded text-white flex items-center"}>
                                    <RomanIV/>
                                </div>
                            );
                            break;
                        }
                        default: {
                            priorityElement = (
                                <div
                                    className={"px-1 bg-teal-400 fill-teal-900 rounded text-white flex items-center"}>
                                    <RomanIV/>
                                </div>
                            );
                            break;
                        }
                    }
                    listData.push({
                        content: (
                            <div className={"flex gap-1 text-sm whitespace-nowrap"}>
                                {priorityElement}
                                <div className={"px-1 bg-teal-500 rounded text-white"}>日程</div>
                                <div>{schedule.name}</div>
                            </div>
                        )
                    });
                }
            }
        });
        return listData || [];
    };

    const monthCellRender = (value: Dayjs) => {
        const listData = getMonthData(value);
        return (
            <ul className="events grid gap-1">
                {listData.map((item, index) => (
                    <li key={"item-" + index}>
                        {item.content}
                    </li>
                ))}
            </ul>
        );
    };

    const dateCellRender = (value: Dayjs) => {
        const listData = getListData(value);
        return (
            <ul className="events grid gap-1">
                {listData.map((item, index) => (
                    <li key={"item-" + index}>
                        {item.content}
                    </li>
                ))}
            </ul>
        );
    };

    const cellRender: CalendarProps<Dayjs>['cellRender'] = (current, info) => {
        if (info.type === 'date') return dateCellRender(current);
        if (info.type === 'month') return monthCellRender(current);
        return info.originNode;
    };

    return (
        <div className={"grid shadow p-6 bg-white rounded-lg"}>
            <Calendar
                cellRender={cellRender}
                onChange={(date) => {
                    // 获取当前点击的日期中的年份
                    emit({
                        group_uuid: searchInfo.group_uuid,
                        start_time: date.startOf("month").toISOString().split("T")[0],
                        end_time: date.endOf("month").toISOString().split("T")[0],
                    });
                }}
            />
        </div>
    );
}
