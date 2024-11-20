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

import {WebInfoEntity} from "../../models/entity/web_info_entity.ts";
import {useSelector} from "react-redux";
import {
    AppstoreAddOutlined,
    BarsOutlined,
    CrownOutlined,
    SearchOutlined,
    UnorderedListOutlined,
    UsergroupAddOutlined
} from "@ant-design/icons";
import {useEffect, useState} from "react";
import {GetScheduleGroupAPI} from "../../interface/schedule_api.ts";
import {ScheduleGroupListDTO} from "../../models/dto/schedule_group_list_dto.ts";
import {Page} from "../../models/page.ts";
import {ScheduleGroupEntity} from "../../models/entity/schedule_group_entity.ts";

export function DashboardGroups({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    const [scheduleGroupList, setScheduleGroupList] = useState<Page<ScheduleGroupEntity>>({} as Page<ScheduleGroupEntity>);
    const [scheduleSearchList, setScheduleSearchList] = useState<ScheduleGroupListDTO>({
        page: 1,
        size: 20,
        type: "all",
    } as ScheduleGroupListDTO);

    document.title = `${webInfo.name} - 日程小组`;
    onHeaderHandler("日程小组");

    // 获取日程小组0
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
    }, [scheduleSearchList]);

    return (
        <>
            <div className={"grid gap-3"}>
                <div className={"flex justify-end"}>
                    <div className={"flex"}>
                        <div onClick={() => null}
                             className={"transition rounded-lg bg-sky-500 hover:bg-sky-600 active:bg-sky-700 text-white px-3 py-1.5 flex items-center space-x-1"}>
                            <AppstoreAddOutlined/>
                            <span>创建小组</span>
                        </div>
                    </div>
                </div>
                <div className={"bg-white rounded-lg p-3 shadow grid gap-6"}>
                    <div className={"grid gap-3"}>
                        <div className={"flex text-lg font-medium space-x-1 items-center"}>
                            <SearchOutlined/>
                            <span>查询小组</span>
                        </div>
                        <div className={"flex space-x-1"}>
                            <label
                                htmlFor="group-search"
                                className="transition relative rounded-md border border-gray-200 shadow-sm focus-within:border-blue-600 focus-within:ring-1 focus-within:ring-blue-600 flex-1 flex"
                            >
                                <input
                                    type="text"
                                    id="group-search"
                                    onChange={(event) => setScheduleSearchList({
                                        ...scheduleSearchList,
                                        search: event.currentTarget.value
                                    })}
                                    className="transition peer border-none bg-transparent placeholder-transparent focus:border-transparent focus:outline-none focus:ring-0 text-sm flex-1 py-1.5"
                                    placeholder={"group-search"}
                                />
                                <span
                                    className="pointer-events-none absolute start-2.5 top-0 -translate-y-1/2 bg-white p-0.5 text-xs text-gray-700 transition-all peer-placeholder-shown:top-1/2 peer-placeholder-shown:text-sm peer-focus:top-0 peer-focus:text-xs"
                                >
                                    组号
                                </span>
                            </label>
                            <button onClick={() => null}
                                    className={"transition rounded-lg bg-sky-500 hover:bg-sky-600 active:bg-sky-700 text-white px-4 md:px-8 lg:px-12 flex items-center space-x-1"}>
                                <SearchOutlined/>
                                <span>查询</span>
                            </button>
                        </div>
                    </div>
                    <div className={"grid gap-3"}>
                        <div className="flex justify-between gap-3">
                            <div className={"flex text-lg font-medium space-x-1 items-center"}>
                                <UnorderedListOutlined/>
                                <span>我的小组</span>
                            </div>
                            <div className={"flex gap-3"}>
                                <div className={"relative flex items-center"}>
                                    <input type="text"
                                           placeholder={"筱锋的小组"}
                                           className={"transition rounded-md border border-gray-200 text-xs py-1 pr-14"}/>
                                    <button
                                        className={"absolute right-0 text-xs rounded-r-md border border-emerald-500 bg-emerald-500 px-2 py-1.5 flex text-white items-center font-extrabold"}>
                                        <SearchOutlined/>
                                    </button>
                                </div>
                                <div className="inline-flex rounded-md border border-gray-100 bg-gray-100 p-0.5 shadow">
                                    <button
                                        onClick={() => setScheduleSearchList({...scheduleSearchList, type: "all"})}
                                        className="transition inline-flex items-center gap-2 rounded-md bg-white px-4 py-0.5 text-sm text-sky-600 shadow-sm focus:relative"
                                    >
                                        <BarsOutlined/>
                                        <span>全部小组</span>
                                    </button>
                                    <button
                                        onClick={() => setScheduleSearchList({...scheduleSearchList, type: "master"})}
                                        className="transition inline-flex items-center gap-1 rounded-md px-4 py-0.5 text-sm text-gray-500 hover:text-gray-700 focus:relative"
                                    >
                                        <CrownOutlined/>
                                        <span>我的小组</span>
                                    </button>
                                    <button
                                        onClick={() => setScheduleSearchList({...scheduleSearchList, type: "join"})}
                                        className="transition inline-flex items-center gap-1 rounded-md px-4 py-0.5 text-sm text-gray-500 hover:text-gray-700 focus:relative"
                                    >
                                        <UsergroupAddOutlined/>
                                        <span>加入的组</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={"grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-3"}>
                    {
                        scheduleGroupList.records?.map((group, index) => {
                            return (
                                <div key={index}
                                     className={"bg-white rounded-lg p-4 shadow-md grid gap-3"}>
                                    <div className={"flex justify-between items-center"}>
                                        <div className={"flex space-x-1 items-center"}>
                                                    <span
                                                        className={"text-lg font-semibold text-gray-800"}>{group.name}</span>
                                        </div>
                                        <div className={"flex gap-3"}>
                                            <button
                                                onClick={() => null}
                                                className={"transition rounded-lg bg-blue-500 hover:bg-blue-600 active:bg-blue-700 text-white px-3 py-1.5 flex items-center space-x-1 shadow-sm"}>
                                                <UsergroupAddOutlined className="text-white"/>
                                                <span className="text-sm">加入</span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            );
                        })
                    }
                </div>
            </div>
        </>
    );
}
