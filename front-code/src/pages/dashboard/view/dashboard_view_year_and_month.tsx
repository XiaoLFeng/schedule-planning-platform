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
import {Dayjs} from "dayjs";
import React from "react";
import {RomanI, RomanIII} from "../../../assets/icon/roman_numerals_svg.tsx";

/**
 * # 看板视图年月
 * 用于展示看板视图年月相关的信息
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
export function DashboardViewYearAndMonth() {

    const getListData = (value: Dayjs) => {
        let listData: { content: React.ReactElement | string }[] = []; // Specify the type of listData
        if (value.month() === 9) {
            switch (value.date()) {
                case 8:
                    listData = [
                        {
                            content: (
                                <div className={"flex gap-1 text-sm whitespace-nowrap"}>
                                    <div className={"px-1 bg-red-400 fill-red-900 rounded text-white flex items-center"}>
                                        <RomanI/></div>
                                    <div className={"px-1 bg-teal-500 rounded text-white"}>课程</div>
                                    <div>物理课</div>
                                </div>
                            )
                        },
                        {
                            content: (
                                <div className={"flex gap-1 text-sm whitespace-nowrap"}>
                                    <div
                                        className={"px-1 bg-green-400 fill-green-900 rounded text-white flex items-center"}>
                                        <RomanIII/></div>
                                    <div className={"px-1 bg-sky-500 rounded text-white"}>日程</div>
                                    <div>学习 SpringMVC 设计原理</div>
                                </div>
                            )
                        },
                    ];
                    break;
                default:
            }
        }
        return listData || [];
    };

    const monthCellRender = (value: Dayjs) => {
        const num = getMonthData(value);
        return num ? (
            <div className="notes-month">
                <section>{num}</section>
                <span>Backlog number</span>
            </div>
        ) : null;
    };

    const dateCellRender = (value: Dayjs) => {
        const listData = getListData(value);
        return (
            <ul className="events grid gap-1">
                {listData.map((item, index) => (
                    <li key={index}>
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
            <Calendar cellRender={cellRender}/>
        </div>
    );
}

const getMonthData = (value: Dayjs) => {
    if (value.month() === 8) {
        return 1394;
    }
};
