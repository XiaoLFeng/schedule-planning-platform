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
import {Route, Routes, useLocation, useNavigate} from "react-router-dom";
import {DashboardViewYearAndMonth} from "./view/dashboard_view_year_and_month.tsx";
import {DashboardViewWeek} from "./view/dashboard_view_week.tsx";
import {DashboardViewDay} from "./view/dashboard_view_day.tsx";
import {animated, useTransition} from "@react-spring/web";
import {useEffect} from "react";
import {DashboardViewMenu} from "../../components/dashboard/dashboard_view_menu.tsx";

export function DashboardView({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {
    const location = useLocation();
    const navigate = useNavigate();
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

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

    return transition((style) => (
        <div className={"grid gap-3"}>
            <div className={"flex justify-between"}>
                <div className={"flex"}>
                    <DashboardViewMenu to={"/dashboard/view/year-and-month"} text={"年/月视图"}
                                       className={"rounded-l-lg"}/>
                    <DashboardViewMenu to={"/dashboard/view/week"} text={"周视图"}/>
                    <DashboardViewMenu to={"/dashboard/view/day"} text={"日视图"} className={"rounded-r-lg"}/>
                </div>
                <div className={"flex"}>
                    <div className={"transition flex gap-1 bg-blue-500 hover:bg-blue-600 active:bg-blue-700 text-white rounded-l-lg px-4 py-1.5"}>
                        <span>课程</span>
                    </div>
                    <div className={"transition flex gap-1 bg-sky-500 hover:bg-sky-600 active:bg-sky-700 text-white px-4 py-1.5"}>
                        <span>特殊日</span>
                    </div>
                    <div className={"transition flex gap-1 bg-green-500 hover:bg-green-600 active:bg-green-700 text-white rounded-r-lg px-4 py-1.5"}>
                        <span>设置</span>
                    </div>
                </div>
            </div>
            <animated.div style={style} className={"w-full h-full"}>
                <Routes>
                    <Route path={"/year-and-month"} element={<DashboardViewYearAndMonth/>}/>
                    <Route path={"/week"} element={<DashboardViewWeek/>}/>
                    <Route path={"/day"} element={<DashboardViewDay/>}/>
                </Routes>
            </animated.div>
        </div>
    ));
}
