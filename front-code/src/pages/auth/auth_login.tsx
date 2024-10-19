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
import {Link} from "react-router-dom";
import React from "react";

/**
 * # 认证登录
 * 用于登录的认证页面；用于登录的认证页面。
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
export function AuthLogin() {
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    document.title = `${webInfo.name} - 登录`;

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
                        <label htmlFor="email" className="sr-only">用户名/邮箱</label>
                        <div className="relative">
                            <input
                                type="text"
                                className="w-full rounded-lg border-gray-200 p-4 pe-12 text-sm shadow-sm transition"
                                placeholder="用户名/邮箱"
                            />
                            <span className="absolute inset-y-0 end-0 grid place-content-center px-4"/>
                        </div>
                    </div>
                    <div>
                        <label htmlFor="password" className="sr-only">密码</label>
                        <div className="relative">
                            <input
                                type="password"
                                className="w-full rounded-lg border-gray-200 p-4 pe-12 text-sm shadow-sm transition"
                                placeholder="输入密码"
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

function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    console.log("登录");
}
