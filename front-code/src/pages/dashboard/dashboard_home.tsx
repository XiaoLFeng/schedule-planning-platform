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
import {message, Timeline, TimelineItemProps} from "antd";
import {useEffect, useState} from "react";
import {BuildOutlined, CalendarOutlined, PlusOutlined} from "@ant-design/icons";
import {RomanI, RomanII, RomanIII, RomanIV} from "../../assets/icon/roman_numerals_svg.tsx";
import {
    GetScheduleGroupAPI,
    GetScheduleListMaybeGroup,
    GetScheduleListWithPriority
} from "../../interface/schedule_api.ts";
import {SchedulePriorityEntity} from "../../models/entity/schedule_priority_entity.ts";
import dayjs from "dayjs";
import {ScheduleGetGroupDTO} from "../../models/dto/schedule_get_group_dto.ts";
import {ScheduleAddModal} from "../../components/modal/schedule_add_modal.tsx";
import {Page} from "../../models/page.ts";
import {ScheduleGroupEntity} from "../../models/entity/schedule_group_entity.ts";
import {ScheduleGroupListDTO} from "../../models/dto/schedule_group_list_dto.ts";
import {Link} from "react-router-dom";

/**
 * # 看板主页
 * @constructor
 */
export function DashboardHome({onHeaderHandler}: Readonly<{ onHeaderHandler: (header: string) => void }>) {
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    const [timeLine, setTimeLine] = useState<TimelineItemProps[]>([] as TimelineItemProps[]);
    const [schedulePriorityEntity, setSchedulePriorityEntity] = useState<SchedulePriorityEntity>({} as SchedulePriorityEntity);
    const [scheduleSearchInfo] = useState<ScheduleGetGroupDTO>({
        // 获取当天所在的周一到周日
        start_time: dayjs().startOf("week").add(-5, "day").toISOString().split("T")[0],
        end_time: dayjs().endOf("week").add(1, "day").toISOString().split("T")[0],
    } as ScheduleGetGroupDTO);

    const [scheduleSearchList] = useState<ScheduleGroupListDTO>({
        page: 1,
        size: 1000,
        type: "all",
    } as ScheduleGroupListDTO);

    const [scheduleAdd, setScheduleAdd] = useState<boolean>(false);
    const [scheduleGroupList, setScheduleGroupList] = useState<Page<ScheduleGroupEntity>>({} as Page<ScheduleGroupEntity>);
    const [refresh, setRefresh] = useState<boolean>(false);

    document.title = `${webInfo.name} - 看板`;
    onHeaderHandler("看板");

    useEffect(() => {
        const func = async () => {
            const getResp = await GetScheduleGroupAPI(scheduleSearchList);
            if (getResp?.output === "Success") {
                setScheduleGroupList(getResp.data!);
            } else {
                console.error(getResp?.error_message);
            }
        }

        func().then();
    }, [refresh]);

    useEffect(() => {
        const func = async () => {
            const getResp = await GetScheduleListWithPriority("week");
            if (getResp?.output === "Success") {
                setSchedulePriorityEntity(getResp.data!);
            } else {
                message.warning(getResp?.error_message);
            }
        }

        const func2 = async () => {
            const getResp = await GetScheduleListMaybeGroup(scheduleSearchInfo);
            if (getResp?.output === "Success") {
                const timeLineItem = [] as TimelineItemProps[];
                getResp.data!.forEach((item) => {
                    let color: string;
                    switch (item.priority) {
                        case 4:
                            color = 'red';
                            break;
                        case 3:
                            color = 'orange';
                            break;
                        case 2:
                            color = 'green';
                            break;
                        case 1:
                            color = 'teal';
                            break;
                        default:
                            color = 'gray';
                            break;
                    }
                    timeLineItem.push({
                        color: color,
                        children: (
                            <div>
                                <div className={"flex space-x-1"}>
                                    <div className={"text-black font-medium"}>{item.name}</div>
                                    <div
                                        className="text-gray-400 text-sm font-thin">{dayjs(item.start_time).format("YYYY-MM-DD HH:mm")}</div>
                                </div>
                                <div
                                    className={"text-gray-500 text-sm"}>{item.description ? item.description : "暂无描述"}</div>
                            </div>
                        ),
                    });
                });
                // 设置 timeLineItem 为按照时间的倒序 (最新的在最上面)
                setTimeLine(timeLineItem.reverse());
            } else {
                message.warning(getResp?.error_message);
            }
        }

        func().then();
        func2().then();
    }, [refresh]);

    return (
        <div className={"flex-1 flex flex-col gap-3 h-full"}>
            <div className={"flex-shrink-0 col-span-2 flex justify-end"}>
                <Link
                    to={"/dashboard/curriculum"}
                    className={"transition flex gap-1 bg-blue-500 hover:bg-blue-600 active:bg-blue-700 text-white rounded-l-lg px-4 py-1.5"}>
                    <BuildOutlined/>
                    <span>课程</span>
                </Link>
                <button
                    onClick={() => setScheduleAdd(true)}
                    className={"transition flex items-center gap-1 bg-green-500 hover:bg-green-600 active:bg-green-700 text-white rounded-r-lg px-4 py-1.5"}>
                    <PlusOutlined/>
                    <span>日程</span>
                </button>
            </div>
            <div className={"flex-1 grid gap-3 grid-cols-2"}>
                <div className={"transition bg-gray-200/75 shadow hover:shadow-lg rounded-lg"}>
                    <div className={"grid gap-1 p-1 h-full"}>
                        <div className={"flex gap-1"}>
                            <div className="flex-1 bg-white rounded-tl-md">
                                <div
                                    className={"bg-red-400 rounded-tl-md ps-3 py-1 text-red-900 fill-red-900 flex gap-1 items-center"}>
                                    <RomanI/>
                                    <span>重要且紧急</span>
                                </div>
                                <div className={"grid p-3 gap-1 w-full"}>
                                    {
                                        schedulePriorityEntity.important?.map((item) => (
                                            <div key={item.schedule_uuid}
                                                 className={"bg-red-100/75 rounded-lg p-3 overflow-ellipsis overflow-hidden whitespace-nowrap"}>
                                                <div className={"flex items-start justify-between space-x-1"}>
                                                    <div className={"text-black font-medium"}>{item.name}</div>
                                                    <div
                                                        className="text-gray-400 text-sm font-thin">{dayjs(item.start_time).format("YYYY-MM-DD HH:mm")}</div>
                                                </div>
                                                <div
                                                    className={"text-gray-500 text-sm truncate"}>{item.description ? item.description : "暂无描述"}</div>
                                            </div>
                                        ))
                                    }
                                </div>
                            </div>
                            <div className="flex-1 bg-white rounded-tr-md">
                                <div
                                    className={"bg-yellow-400 rounded-tr-md ps-3 py-1 text-yellow-900 fill-yellow-900 flex gap-1 items-center"}>
                                    <RomanII/>
                                    <span>重要不紧急</span>
                                </div>
                                <div className={"grid p-3 gap-1 w-full"}>
                                    {
                                        schedulePriorityEntity.normal?.map((item) => (
                                            <div key={item.schedule_uuid}
                                                 className={"bg-yellow-100/75 rounded-lg p-3 overflow-ellipsis overflow-hidden whitespace-nowrap"}>
                                                <div className={"flex items-start justify-between space-x-1"}>
                                                    <div className={"text-black font-medium"}>{item.name}</div>
                                                    <div
                                                        className="text-gray-400 text-sm font-thin">{dayjs(item.start_time).format("YYYY-MM-DD HH:mm")}</div>
                                                </div>
                                                <div
                                                    className={"text-gray-500 text-sm truncate"}>{item.description ? item.description : "暂无描述"}</div>
                                            </div>
                                        ))
                                    }
                                </div>
                            </div>
                        </div>
                        <div className={"flex gap-1"}>
                            <div className="flex-1 bg-white rounded-bl-md">
                                <div
                                    className={"bg-green-400 ps-3 py-1 text-green-900 fill-green-900 flex gap-1 items-center"}>
                                    <RomanIII/>
                                    <span>紧急不重要</span>
                                </div>
                                <div className={"grid p-3 gap-1 w-full"}>
                                    {
                                        schedulePriorityEntity.general?.map((item) => (
                                            <div key={item.schedule_uuid}
                                                 className={"bg-green-100/75 rounded-lg p-3 overflow-ellipsis overflow-hidden whitespace-nowrap"}>
                                                <div className={"flex items-start justify-between space-x-1"}>
                                                    <div className={"text-black font-medium"}>{item.name}</div>
                                                    <div
                                                        className="text-gray-400 text-sm font-thin">{dayjs(item.start_time).format("YYYY-MM-DD HH:mm")}</div>
                                                </div>
                                                <div
                                                    className={"text-gray-500 text-sm truncate"}>{item.description ? item.description : "暂无描述"}</div>
                                            </div>
                                        ))
                                    }
                                </div>
                            </div>
                            <div className="flex-1 bg-white rounded-br-md">
                                <div
                                    className={"bg-teal-400 ps-3 py-1 text-teal-900 fill-teal-900 flex gap-1 items-center"}>
                                    <RomanIV/>
                                    <span>不重要不紧急</span>
                                </div>
                                <div className={"grid p-3 gap-1 w-full"}>
                                    {
                                        schedulePriorityEntity.low?.map((item) => (
                                            <div key={item.schedule_uuid}
                                                 className={"bg-teal-100/75 rounded-lg p-3 overflow-ellipsis overflow-hidden whitespace-nowrap"}>
                                                <div className={"flex items-start justify-between space-x-1"}>
                                                    <div className={"text-black font-medium"}>{item.name}</div>
                                                    <div
                                                        className="text-gray-400 text-sm font-thin">{dayjs(item.start_time).format("YYYY-MM-DD HH:mm")}</div>
                                                </div>
                                                <div
                                                    className={"text-gray-500 text-sm truncate"}>{item.description ? item.description : "暂无描述"}</div>
                                            </div>
                                        ))
                                    }
                                </div>
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
            <ScheduleAddModal propOpen={scheduleAdd} groupList={scheduleGroupList}
                              emit={setScheduleAdd} refresh={setRefresh}/>
        </div>
    );
}
