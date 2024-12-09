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
import {Link, Route, Routes, useLocation, useNavigate} from "react-router-dom";
import {DashboardViewYearAndMonth} from "./view/dashboard_view_year_and_month.tsx";
import {DashboardViewWeek} from "./view/dashboard_view_week.tsx";
import {DashboardViewDay} from "./view/dashboard_view_day.tsx";
import {animated, useTransition} from "@react-spring/web";
import {useEffect, useState} from "react";
import {DashboardViewMenu} from "../../components/dashboard/dashboard_view_menu.tsx";
import {Page} from "../../models/page.ts";
import {ScheduleGroupEntity} from "../../models/entity/schedule_group_entity.ts";
import {GetScheduleGroupAPI, GetScheduleListMaybeGroup} from "../../interface/schedule_api.ts";
import {ScheduleGroupListDTO} from "../../models/dto/schedule_group_list_dto.ts";
import {ScheduleAddModal} from "../../components/modal/schedule_add_modal.tsx";
import {ScheduleEntity} from "../../models/dto/schedule_entity.ts";
import {ScheduleGetGroupDTO} from "../../models/dto/schedule_get_group_dto.ts";

export function DashboardView({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {

    const location = useLocation();
    const navigate = useNavigate();
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    const [scheduleAdd, setScheduleAdd] = useState<boolean>(false);
    const [selectedGroup, setSelectedGroup] = useState<string>("");
    const [refresh, setRefresh] = useState<boolean>(false);
    const [scheduleList, setScheduleList] = useState<ScheduleEntity[]>({} as ScheduleEntity[]);
    const [scheduleSearchInfo, setScheduleSearchInfo] = useState<ScheduleGetGroupDTO>({
        // 获取当年第一天和月末最后一天 （格式 yyyy-MM-dd）
        start_time: new Date(new Date().getFullYear(), 0, 1).toISOString().split("T")[0],
        end_time: new Date(new Date().getFullYear(), 11, 31).toISOString().split("T")[0],
    } as ScheduleGetGroupDTO);

    const [scheduleGroupList, setScheduleGroupList] = useState<Page<ScheduleGroupEntity>>({} as Page<ScheduleGroupEntity>);
    const [scheduleSearchList] = useState<ScheduleGroupListDTO>({
        page: 1,
        size: 1000,
        type: "all",
    } as ScheduleGroupListDTO);

    document.title = `${webInfo.name} - 视图`;
    onHeaderHandler("视图");


    const transition = useTransition(location, {
        from: {opacity: 0},
        enter: {opacity: 1},
        config: {duration: 200}
    });

    useEffect(() => {
        if (location.pathname === "/dashboard/view") {
            navigate("/dashboard/view/year-and-month");
        }
    });

    useEffect(() => {
        const func = async () => {
            const getResp = await GetScheduleListMaybeGroup(scheduleSearchInfo);
            if (getResp?.output === "Success") {
                setScheduleList(getResp.data!);
                console.log(getResp.data);
            } else {
                console.error(getResp?.error_message);
            }
        }

        if (location.pathname !== "/dashboard/view") {
            func().then();
        }
    }, [location.pathname, scheduleSearchInfo, refresh]);

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

    return transition((style) => (
        <>
            <div className={"grid gap-3"}>
                <div className={"flex justify-between"}>
                    <div className={"flex"}>
                        <DashboardViewMenu to={"/dashboard/view/year-and-month"} text={"年/月视图"}
                                           className={"rounded-l-lg"}/>
                        <DashboardViewMenu to={"/dashboard/view/week"} text={"周视图"}/>
                        <DashboardViewMenu to={"/dashboard/view/day"} text={"日视图"} className={"rounded-r-lg"}/>
                    </div>
                    <div className={"flex gap-3"}>
                        <div>
                            <select
                                name="group"
                                id="group"
                                onChange={(e) => {
                                    setSelectedGroup(e.target.value);
                                    if (e.target.value) {
                                        setScheduleSearchInfo({...scheduleSearchInfo, group_uuid: e.target.value});
                                    } else {
                                        setScheduleSearchInfo({...scheduleSearchInfo, group_uuid: undefined});
                                    }
                                }}
                                className="w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm"
                            >
                                <option value="">全部</option>
                                {
                                    scheduleGroupList.records?.map((item) => (
                                        <option key={item.group_uuid} value={item.group_uuid}>{item.name}</option>
                                    ))
                                }
                            </select>
                        </div>
                        <div className={"flex"}>
                            <button
                                onClick={() => setScheduleAdd(true)}
                                className={"transition flex gap-1 bg-blue-500 hover:bg-blue-600 active:bg-blue-700 text-white rounded-l-lg px-4 py-1.5"}>
                                <span>添加日程</span>
                            </button>
                            <Link
                                to={"/dashboard/curriculum"}
                                className={"transition flex gap-1 bg-sky-500 hover:bg-sky-600 active:bg-sky-700 text-white rounded-r-lg px-4 py-1.5"}>
                                <span>添加课程</span>
                            </Link>
                        </div>
                    </div>
                </div>
                <animated.div style={style} className={"w-full h-full"}>
                    <Routes>
                        <Route path={"/year-and-month"}
                               element={
                                   <DashboardViewYearAndMonth
                                       getScheduleData={scheduleList}
                                       emit={setScheduleSearchInfo}
                                       searchInfo={scheduleSearchInfo}
                                   />
                               }/>
                        <Route path={"/week"} element={<DashboardViewWeek/>}/>
                        <Route path={"/day"} element={<DashboardViewDay/>}/>
                    </Routes>
                </animated.div>
            </div>
            <ScheduleAddModal propOpen={scheduleAdd} groupUuid={selectedGroup} groupList={scheduleGroupList}
                              emit={setScheduleAdd} refresh={setRefresh}/>
        </>
    ));
}
