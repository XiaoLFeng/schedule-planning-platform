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
import {Link, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {AuthLoginAPI} from "../../interface/auth_api.ts";
import {AuthLoginDTO} from "../../models/dto/auth_login_dto.ts";
import {message} from "antd";
import Cookies from "js-cookie";

/**
 * # 认证登录
 * 用于登录的认证页面；用于登录的认证页面。
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
export function AuthLogin() {
    const navigate = useNavigate();

    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);
    const [authLogin, setAuthLogin] = useState<AuthLoginDTO>({} as AuthLoginDTO);

    document.title = `${webInfo.name} - 登录`;

    useEffect(() => {
        if (authLogin.user) {
            document.getElementById("user")?.classList.remove("border-red-500");
            document.getElementById("user_label")?.classList.remove("text-red-500");
        }
        if (authLogin.password) {
            document.getElementById("password")?.classList.remove("border-red-500");
            document.getElementById("password_label")?.classList.remove("text-red-500");
        }
    }, [authLogin.password, authLogin.user]);

    async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();
        document.getElementById("user")?.classList.remove("border-red-500");
        document.getElementById("user_label")?.classList.remove("text-red-500");
        document.getElementById("password")?.classList.remove("border-red-500");
        document.getElementById("password_label")?.classList.remove("text-red-500");
        // 数据检查
        if (!authLogin.user || !authLogin.password) {
            message.warning("用户名/邮箱和密码不能为空");
            if (!authLogin.user) {
                document.getElementById("user")?.classList.add("border-red-500");
                document.getElementById("user_label")?.classList.add("text-red-500");
            }
            if (!authLogin.password) {
                document.getElementById("password")?.classList.add("border-red-500");
                document.getElementById("password_label")?.classList.add("text-red-500");
            }
            return;
        }
        const getResp = await AuthLoginAPI(authLogin);
        if (getResp?.output === "Success") {
            message.info(`欢迎回来 ${getResp.data!.user.username}`);
            // 存储 Token 信息
            Cookies.set("Authorization", getResp.data!.token, {expires: 1});
            Cookies.set("X-User-UUID", getResp.data!.user.uuid, {expires: 1});
            // 跳转到登录
            setTimeout(() => {
                navigate("/dashboard/home");
            }, 100);
        } else {
            message.warning(getResp?.error_message);
            document.getElementById("password")?.setAttribute("value", "");
        }
    }

    return (
        <div className="mx-auto max-w-screen-xl px-4 py-16 sm:px-6 lg:px-8">
            <div className="mx-auto max-w-lg">
                <h1 className="text-center text-2xl font-bold text-cyan-600 sm:text-3xl">开始使用</h1>
                <p className="mx-auto mt-4 max-w-md text-center text-gray-500">
                    学生日程规划平台旨在为学生提供一个全面、高效的日程管理工具，帮助他们合理规划学习与生活，提高时间管理能力。
                </p>
                <form onSubmit={handleSubmit} className="mb-0 mt-6 space-y-4 rounded-lg p-4 shadow-lg sm:p-6 lg:p-8 bg-white">
                    <p className="text-center text-lg font-medium">登录你的账号</p>
                    <div>
                        <label id={"user_label"} htmlFor="user" className="sr-only">用户名/邮箱</label>
                        <div className="relative">
                            <input
                                type="text"
                                className="w-full rounded-lg border-gray-200 p-4 pe-12 text-sm shadow-sm transition"
                                placeholder="用户名/邮箱"
                                id={"user"}
                                onInput={(event) => setAuthLogin({...authLogin, user: event.currentTarget.value})}
                            />
                            <span className="absolute inset-y-0 end-0 grid place-content-center px-4"/>
                        </div>
                    </div>
                    <div>
                        <label id={"password_label"} htmlFor="password" className="sr-only">密码</label>
                        <div className="relative">
                            <input
                                type="password"
                                className="w-full rounded-lg border-gray-200 p-4 pe-12 text-sm shadow-sm transition"
                                placeholder="输入密码"
                                id={"password"}
                                onInput={(event) => setAuthLogin({...authLogin, password: event.currentTarget.value})}
                            />
                            <span className="absolute inset-y-0 end-0 grid place-content-center px-4"/>
                        </div>
                    </div>
                    <button type="submit"
                            className="block w-full rounded-lg bg-teal-500 px-5 py-3 text-sm font-medium text-white">
                        登 录
                    </button>
                    <p className="text-center text-sm text-gray-500">
                        <span>还没有账号? </span>
                        <Link className="underline" to="/auth/register">注册</Link>
                    </p>
                </form>
            </div>
        </div>
    );
}
