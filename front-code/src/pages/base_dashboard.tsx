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

import {Route, Routes, useLocation} from "react-router-dom";
import {DashboardHome} from "./dashboard/dashboard_home.tsx";
import {DashboardSideMenu} from "../components/dashboard/dashboard_side_menu.tsx";
import {useSelector} from "react-redux";
import {WebInfoEntity} from "../models/entity/web_info_entity.ts";
import {useState} from "react";
import {animated, useTransition} from "@react-spring/web";
import {DashboardView} from "./dashboard/dashboard_view.tsx";

/**
 * # 基础仪表板
 * 用于展示基础仪表板的页面；用于展示基础仪表板的页面。
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
export function BaseDashboard() {
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);
    const location = useLocation();
    const [header, setHeader] = useState<string>("");
    const transition = useTransition(location, {
        from: location.pathname.startsWith("/dashboard/view/") ? {opacity: 1} : {opacity: 0},
        enter: location.pathname.startsWith("/dashboard/view/") ? {} : {opacity: 1},
        config: location.pathname.startsWith("/dashboard/view/") ? {} : {duration: 200}
    });

    function handlerHeader(value: string) {
        setHeader(value);
    }

    return transition((style, items) => (
        <div className={"min-h-dvh bg-gray-100"}>
            <div className={"min-h-dvh fixed left-0 top-0"}>
                <DashboardSideMenu webInfo={webInfo}/>
            </div>
            <div className={"ps-72 p-9 min-h-dvh grid"}>
                <div className={"flex flex-col gap-3 h-full"}>
                    <div className={"flex-shrink-0 text-2xl font-bold"}>
                        {header}
                    </div>
                    <animated.div style={style} className={"flex-1"}>
                        <Routes location={items}>
                            <Route path={"/home"} element={<DashboardHome onHeaderHandler={handlerHeader}/>}/>
                            <Route path={"/view/*"} element={<DashboardView onHeaderHandler={handlerHeader}/>}/>
                        </Routes>
                    </animated.div>
                </div>
            </div>
        </div>
    ));
}
