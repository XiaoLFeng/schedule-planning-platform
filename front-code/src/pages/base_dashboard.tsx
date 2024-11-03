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

import {Route, Routes, useLocation, useNavigate} from "react-router-dom";
import {DashboardHome} from "./dashboard/dashboard_home.tsx";
import {DashboardSideMenu} from "../components/dashboard/dashboard_side_menu.tsx";
import {useDispatch, useSelector} from "react-redux";
import {WebInfoEntity} from "../models/entity/web_info_entity.ts";
import {useEffect, useRef, useState} from "react";
import {animated, useSpring, useTransition} from "@react-spring/web";
import {DashboardView} from "./dashboard/dashboard_view.tsx";
import {UserCurrentAPI} from "../interface/user_api.ts";
import Cookies from "js-cookie";
import {UserEntity} from "../models/entity/user_entity.ts";
import {setUser} from "../store/user_store.ts";
import {message} from "antd";
import {DashboardCurriculum} from "./dashboard/dashboard_curriculum.tsx";

/**
 * # 基础仪表板
 * 用于展示基础仪表板的页面；用于展示基础仪表板的页面。
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
export function BaseDashboard() {
    const location = useLocation();
    const navigate = useNavigate();

    const dispatch = useDispatch();
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);
    const [header, setHeader] = useState<string>("");
    const transition = useTransition(location, {
        from: location.pathname.startsWith("/dashboard/view/") ? {opacity: 1} : {opacity: 0},
        enter: location.pathname.startsWith("/dashboard/view/") ? {} : {opacity: 1},
        config: location.pathname.startsWith("/dashboard/view/") ? {} : {duration: 200}
    });
    const spring = useSpring({
        from: {opacity: 0},
        to: {opacity: 1},
        config: {duration: 200}
    });

    const checkLoginTimeout = useRef<number>(0);

    // 获取当前用户信息
    useEffect(() => {
        const func = async () => {
            const getResp = await UserCurrentAPI(Cookies.get("X-User-UUID")!);
            if (getResp?.output === "Success") {
                dispatch(setUser(getResp.data! as UserEntity));
            } else {
                if (!checkLoginTimeout.current) {
                    checkLoginTimeout.current = setTimeout(() => {
                        Cookies.remove("X-User-UUID");
                        Cookies.remove("Authorization");
                        message.warning("登录已过期，请重新登录！");
                        navigate("/auth/login");
                    });
                }
            }
        }
        func().then();
    }, [dispatch, navigate]);

    // 路由重定向
    useEffect(() => {
        if (location.pathname === "/dashboard") {
            navigate("/dashboard/home");
        }
    }, [location.pathname, navigate]);

    return transition((style, items) => (
        <div className={"min-h-dvh bg-gray-100"}>
            <div className={"min-h-dvh fixed left-0 top-0"}>
                <DashboardSideMenu webInfo={webInfo}/>
            </div>
            <div className={"ps-72 p-9 min-h-dvh grid"}>
                <div className={"flex flex-col gap-3 h-full"}>
                    <animated.div style={spring} className={"flex gap-3"}>

                    </animated.div>
                    <div className={"flex-shrink-0 text-2xl font-bold"}>
                        {header}
                    </div>
                    <animated.div style={style} className={"flex-1"}>
                        <Routes location={items}>
                            <Route path={"/home"} element={<DashboardHome onHeaderHandler={setHeader}/>}/>
                            <Route path={"/view/*"} element={<DashboardView onHeaderHandler={setHeader}/>}/>
                            <Route path={"/curriculum/*"} element={<DashboardCurriculum onHeaderHandler={setHeader}/>}/>
                        </Routes>
                    </animated.div>
                </div>
            </div>
        </div>
    ));
}
