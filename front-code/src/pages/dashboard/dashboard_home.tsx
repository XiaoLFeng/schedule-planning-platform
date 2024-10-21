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

import {useSelector} from "react-redux";
import {WebInfoEntity} from "../../models/entity/web_info_entity.ts";
import {Timeline, TimelineItemProps} from "antd";
import {useEffect, useState} from "react";
import {BuildOutlined, CalendarOutlined, PlusOutlined} from "@ant-design/icons";
import {RomanI, RomanII, RomanIII, RomanIV} from "../../assets/icon/roman_numerals_svg.tsx";

/**
 * # 看板主页
 * @constructor
 */
export function DashboardHome({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);
    const [timeLine, setTimeLine] = useState<TimelineItemProps[]>([] as TimelineItemProps[]);

    document.title = `${webInfo.name} - 看板`;
    onHeaderHandler("看板");

    useEffect(() => {
        setTimeLine([
            {
                color: 'green',
                children: 'Create a services site 2015-09-01',
            },
            {
                color: 'green',
                children: 'Create a services site 2015-09-01',
            },
        ] as TimelineItemProps[]);
    }, []);

    return (
        <div className={"flex-1 flex flex-col gap-3 h-full"}>
            <div className={"flex-shrink-0 col-span-2 flex justify-end"}>
                <div
                    className={"transition flex gap-1 bg-blue-500 hover:bg-blue-600 active:bg-blue-700 text-white rounded-l-lg px-4 py-1.5"}>
                    <BuildOutlined/>
                    <span>课程</span>
                </div>
                <div
                    className={"transition flex gap-1 bg-sky-500 hover:bg-sky-600 active:bg-sky-700 text-white px-4 py-1.5"}>
                    <CalendarOutlined/>
                    <span>特殊日</span>
                </div>
                <div
                    className={"transition flex gap-1 bg-green-500 hover:bg-green-600 active:bg-green-700 text-white rounded-r-lg px-4 py-1.5"}>
                    <PlusOutlined/>
                    <span>日程</span>
                </div>
            </div>
            <div className={"flex-1 grid gap-3 grid-cols-2"}>
                <div className={"transition bg-gray-200/75 shadow hover:shadow-lg rounded-lg"}>
                    <div className={"grid gap-1 p-1 h-full"}>
                        <div className={"flex gap-1"}>
                            <div className="flex-1 bg-white rounded-tl-md">
                                <div className={"bg-red-400 rounded-tl-md ps-3 py-1 text-red-900 fill-red-900 flex gap-1 items-center"}>
                                    <RomanI/>
                                    <span>重要且紧急</span>
                                </div>
                                <div className={"p-3"}>内容1</div>
                            </div>
                            <div className="flex-1 bg-white rounded-tr-md">
                                <div className={"bg-yellow-400 rounded-tr-md ps-3 py-1 text-yellow-900 fill-yellow-900 flex gap-1 items-center"}>
                                    <RomanII/>
                                    <span>重要不紧急</span>
                                </div>
                                <div className={"p-3"}>内容1</div>
                            </div>
                        </div>
                        <div className={"flex gap-1"}>
                            <div className="flex-1 bg-white rounded-bl-md">
                                <div className={"bg-green-400 ps-3 py-1 text-green-900 fill-green-900 flex gap-1 items-center"}>
                                    <RomanIII/>
                                    <span>紧急不重要</span>
                                </div>
                                <div className={"p-3"}>内容1</div>
                            </div>
                            <div className="flex-1 bg-white rounded-br-md">
                                <div className={"bg-teal-400 ps-3 py-1 text-teal-900 fill-teal-900 flex gap-1 items-center"}>
                                    <RomanIV/>
                                    <span>不重要不紧急</span>
                                </div>
                                <div className={"p-3"}>内容1</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={"transition bg-white shadow hover:shadow-lg rounded-lg p-6"}>
                    <div className={"mb-3 text-xl"}>
                        最近日程
                    </div>
                    <Timeline items={timeLine} className={"p-3"}/>
                </div>
            </div>
        </div>
    );
}
