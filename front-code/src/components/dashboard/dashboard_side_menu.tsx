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

import {Link, useLocation, useNavigate} from "react-router-dom";
import {WebInfoEntity} from "../../models/entity/web_info_entity.ts";
import {
    CalendarOutlined,
    DashboardOutlined,
    PicLeftOutlined,
    SettingOutlined,
    SlidersOutlined
} from "@ant-design/icons";
import {JSX, useEffect, useRef, useState} from "react";

import avatar1 from "../../assets/images/avatar_1.webp";
import avatar2 from "../../assets/images/avatar_2.webp";
import avatar3 from "../../assets/images/avatar_3.webp";
import avatar4 from "../../assets/images/avatar_4.webp";
import {useSelector} from "react-redux";
import {UserEntity} from "../../models/entity/user_entity.ts";
import {message, Modal} from "antd";
import {AuthLogoutAPI} from "../../interface/auth_api.ts";

/**
 * # 仪表板侧边菜单
 * 用于展示仪表板侧边菜单的页面；用于展示仪表板侧边菜单的页面。
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
export function DashboardSideMenu({webInfo}: { webInfo: WebInfoEntity }) {
    const userEntity = useSelector((state: { userCurrent: UserEntity }) => state.userCurrent);

    const navigate = useNavigate();

    function handlerAvatar() {
        const avatars = [avatar1, avatar2, avatar3, avatar4];
        return avatars[Math.floor(Math.random() * avatars.length)];
    }

    const [openModal, setOpenModal] = useState(false);

    async function handleOk() {
        const getResp = await AuthLogoutAPI();
        if (getResp?.output === "Success") {
            navigate("/auth/login");
            message.success("登出成功");
        } else {
            message.warning(getResp?.error_message);
        }
        setOpenModal(false);
    }

    function handleCancel() {
        setOpenModal(false);
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
                    <SideMenuItem icon={<PicLeftOutlined/>} title={"课程表"} path={"/dashboard/curriculum"}/>
                    <SideMenuItem icon={<SettingOutlined/>} title={"设置"} path={"/dashboard/settings"}/>
                    <SideMenuItem icon={<SlidersOutlined/>} title={"管理"} path={"/admin/home"}/>
                </div>
            </div>
            <div className={"flex-shrink-0 text-white"}>
                <button type={"button"} className={"border-t p-4 border-gray-500 w-full text-left"}
                        onClick={() => setOpenModal(true)}>
                    <div className={"flex justify-center gap-1"}>
                        <div className={"size-12 flex-shrink-0"}>
                            <img src={handlerAvatar()} alt={webInfo.name} className={"rounded-xl"}/>
                        </div>
                        <div className={"ml-2 flex-1 grid items-center"}>
                            <div className={"grid"}>
                                <div className={"font-bold"}>{userEntity.username}</div>
                                <div className={"text-sm text-gray-400"}>{userEntity.email}</div>
                            </div>
                        </div>
                    </div>
                </button>
            </div>
            <Modal
                open={openModal}
                title="确认登出"
                onOk={handleOk}
                onCancel={handleCancel}
                footer={
                    <div className={"flex gap-3 justify-end"}>
                        <button type={"button"}
                                className={"transition border border-gray-200 shadow-sm hover:bg-gray-200 px-4 py-1.5 rounded-lg"}
                                onClick={handleCancel}>
                            取消
                        </button>
                        <button type={"button"}
                                className={"transition border border-gray-200 shadow-sm bg-red-500 hover:bg-red-600 px-4 py-1.5 text-white rounded-lg"}
                                onClick={handleOk}>
                            确定
                        </button>
                    </div>
                }
            >
                <div className={"grid gap-1"}>
                    <div className={"flex"}>
                        <span>是否退出当前</span>
                        <span className={"text-red-500 font-bold"}>「{userEntity.username}」</span>
                        <span>账号</span>
                    </div>
                </div>
            </Modal>
        </div>
    );
}

/**
 * # 侧边菜单项
 * 用于展示侧边菜单项的页面；用于展示侧边菜单项的页面。
 *
 * @param icon 图标
 * @param title 标题
 * @param path 路径
 * @returns {JSX.Element} 侧边菜单项
 */
function SideMenuItem({icon, title, path}: { icon: JSX.Element, title: string, path: string }) {
    const getData = () => {
        if (location.pathname.startsWith(path)) {
            return "transition text-white flex gap-2 px-4 py-2 rounded-lg shadow bg-gray-700";
        } else {
            return "transition text-white flex gap-2 px-4 py-2 rounded-lg";
        }
    }

    const location = useLocation();
    const clazz = useRef<string>(getData());
    const [element, setElement] = useState<JSX.Element>(
        <Link to={path} className={clazz.current}>
            {icon}
            <span>{title}</span>
        </Link>
    );

    useEffect(() => {
        if (location.pathname === path) {
            setElement(
                <div className={clazz.current}>
                    {icon}
                    <span>{title}</span>
                </div>
            );
        } else {
            setElement(
                <Link to={path} className={clazz.current}>
                    {icon}
                    <span>{title}</span>
                </Link>
            );
        }
    }, [icon, location.pathname, path, title]);

    function Element() {
        return element;
    }

    return (
        <Element/>
    );
}
