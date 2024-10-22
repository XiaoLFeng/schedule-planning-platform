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

import {Timeline} from "antd";
import dayjs from "dayjs";

export function DashboardViewDay() {
    const date = dayjs();

    function handlerWeek() {
        const week = ["周一", "周二", "周三", "周四", "周五", "周六", "周日"];
        switch (date.format("dddd")) {
            case "Monday":
                return week[0];
            case "Tuesday":
                return week[1];
            case "Wednesday":
                return week[2];
            case "Thursday":
                return week[3];
            case "Friday":
                return week[4];
            case "Saturday":
                return week[5];
            case "Sunday":
                return week[6];
            default:
                return "未知";
        }
    }

    return (
        <div className={"grid grid-cols-2 gap-3"}>
            <div className={"block"}>
                <div className={"bg-white rounded-lg shadow p-6"}>
                    <div className={"grid gap-3 text-center"}>
                        <div className={"grid mb-3"}>
                            <div className={"text-xl font-bold"}>{date.format("YYYY-MM-DD")}</div>
                            <div className={"text-sm text-gray-500"}>{handlerWeek()}</div>
                        </div>
                        <div className={"grid"}>
                        <Timeline
                                mode={"left"}
                                items={[
                                    {
                                        label: '2015-09-01',
                                        children: 'Create a services',
                                        color: 'green',
                                    },
                                    {
                                        label: '2015-09-01 09:12:11',
                                        children: 'Solve initial network problems',
                                    },
                                    {
                                        children: 'Technical testing',
                                    },
                                    {
                                        label: '2015-09-01 09:12:11',
                                        children: 'Network problems being solved',
                                    },
                                    {
                                        label: '2015-09-01 09:12:11',
                                        children: 'Network problems being solved',
                                    },
                                    {
                                        label: '2015-09-01 09:12:11',
                                        children: 'Network problems being solved',
                                    },
                                ]}
                            />
                        </div>
                    </div>
                </div>
            </div>
            <div className={"block"}>
                <div className={"bg-white rounded-lg shadow p-6"}>
                    day
                </div>
            </div>
        </div>
    );
}
