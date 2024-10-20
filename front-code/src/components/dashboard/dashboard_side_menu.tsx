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

import {Link, useLocation} from "react-router-dom";
import {WebInfoEntity} from "../../models/entity/web_info_entity.ts";
import {
    CalendarOutlined,
    DashboardOutlined,
    PicLeftOutlined,
    SettingOutlined,
    SlidersOutlined
} from "@ant-design/icons";
import {JSX, useEffect, useState} from "react";

import avatar1 from "../../assets/images/avatar_1.webp";
import avatar2 from "../../assets/images/avatar_2.webp";
import avatar3 from "../../assets/images/avatar_3.webp";
import avatar4 from "../../assets/images/avatar_4.webp";

/**
 * # 仪表板侧边菜单
 * 用于展示仪表板侧边菜单的页面；用于展示仪表板侧边菜单的页面。
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
export function DashboardSideMenu({webInfo}: { webInfo: WebInfoEntity }) {

    function handlerAvatar() {
        const avatars = [avatar1, avatar2, avatar3, avatar4];
        return avatars[Math.floor(Math.random() * avatars.length)];
    }

    return (
        <div className={"w-64 min-h-dvh bg-gray-600 shadow-2xl flex flex-col"}>
            <div className={"flex-1"}>
                <div className={"p-4 flex justify-center"}>
                    <div className={"text-white font-bold text-2xl"}>{webInfo.name}</div>
                </div>
                <div className={"px-16 mb-3"}>
                    <hr/>
                </div>
                <div className={"p-4 space-y-1 min-h-max"}>
                    <SideMenuItem icon={<DashboardOutlined/>} title={"看板"} path={"/dashboard/home"}/>
                    <SideMenuItem icon={<CalendarOutlined/>} title={"视图"} path={"/dashboard/view"}/>
                    <SideMenuItem icon={<PicLeftOutlined/>} title={"纪念日"} path={"/dashboard/anniversary"}/>
                    <SideMenuItem icon={<SettingOutlined/>} title={"设置"} path={"/dashboard/settings"}/>
                    <SideMenuItem icon={<SlidersOutlined />} title={"管理"} path={"/admin/home"}/>
                </div>
            </div>
            <div className={"flex-shrink-0 text-white"}>
            <div className={"border-t p-4 border-gray-500"}>
                    <div className={"flex justify-center gap-1"}>
                        <div className={"size-12 flex-shrink-0"}>
                            <img src={handlerAvatar()} alt={webInfo.name} className={"rounded-xl"}/>
                        </div>
                        <div className={"ml-2 flex-1 grid items-center"}>
                            <div className={"grid"}>
                                <div className={"font-bold"}>用户</div>
                                <div className={"text-sm"}>邮箱</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

function SideMenuItem({icon, title, path}: { icon: JSX.Element, title: string, path: string }) {
    const location = useLocation();
    const [clazz, setClazz] = useState<string>();

    useEffect(() => {
        if (location.pathname === path) {
            setClazz("transition text-white flex gap-2 px-4 py-2 rounded-lg shadow bg-gray-700");
        } else {
            setClazz("transition text-white flex gap-2 px-4 py-2 rounded-lg");
        }
    }, [location.pathname, path]);

    return (
        <Link to={path} className={clazz}>
            {icon}
            <span>{title}</span>
        </Link>
    );
}
