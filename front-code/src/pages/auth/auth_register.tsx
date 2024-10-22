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

import backgroundImage from "../../assets/images/register-background.webp";
import {Link, useNavigate} from "react-router-dom";
import {RegisterInput} from "../../components/register/register_input.tsx";
import React, {useRef, useState} from "react";
import {AuthRegisterDTO} from "../../models/dto/auth_register_dto.ts";
import {LogoSVG} from "../../assets/icon/logo_svg.tsx";
import {message} from "antd";
import {AuthRegisterAPI} from "../../interface/auth_api.ts";
import Cookies from "js-cookie";
import {animated, useSpring} from "@react-spring/web";

/**
 * # 注册页面
 * 用于用户注册页面；用于用户注册新账号。
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
export function AuthRegister() {
    const navigate = useNavigate();
    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);
    const [authRegister, setAuthRegister] = useState<AuthRegisterDTO>({} as AuthRegisterDTO);
    const confirmPassword = useRef<string>("");

    const prop = useSpring({
        from: {opacity: 0},
        to: {opacity: 1},
        config: {duration: 200}
    });

    document.title = `${webInfo.name} - 注册`;

    async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();
        document.getElementById("email")?.classList.remove("border-red-500");
        document.getElementById("email_label")?.classList.remove("text-red-500");
        document.getElementById("phone")?.classList.remove("border-red-500");
        document.getElementById("phone_label")?.classList.remove("text-red-500");
        document.getElementById("username")?.classList.remove("border-red-500");
        document.getElementById("username_label")?.classList.remove("text-red-500");
        document.getElementById("password")?.classList.remove("border-red-500");
        document.getElementById("password_label")?.classList.remove("text-red-500");
        // 检查信息是否填写完整
        if (!authRegister.email || !authRegister.phone || !authRegister.username || !authRegister.password) {
            message.warning("请填写完整信息");
            if (!authRegister.email) {
                document.getElementById("email")?.classList.add("border-red-500");
                document.getElementById("email_label")?.classList.add("text-red-500");
            }
            if (!authRegister.phone) {
                document.getElementById("phone")?.classList.add("border-red-500");
                document.getElementById("phone_label")?.classList.add("text-red-500");
            }
            if (!authRegister.username) {
                document.getElementById("username")?.classList.add("border-red-500");
                document.getElementById("username_label")?.classList.add("text-red-500");
            }
            if (!authRegister.password) {
                document.getElementById("password")?.classList.add("border-red-500");
                document.getElementById("password_label")?.classList.add("text-red-500");
            }
            if (!confirmPassword.current) {
                document.getElementById("password_confirmation")?.classList.add("border-red-500");
                document.getElementById("password_confirmation_label")?.classList.add("text-red-500");
            }
            return;
        }
        // 验证密码
        if (authRegister.password !== confirmPassword.current) {
            message.warning("两次输入的密码不一致");
            document.getElementById("password")?.classList.add("border-red-500");
            document.getElementById("password_label")?.classList.add("text-red-500");
            document.getElementById("password_confirmation")?.classList.add("border-red-500");
            document.getElementById("password_confirmation_label")?.classList.add("text-red-500");
            return;
        }
        // 提交注册信息
        const getResp = await AuthRegisterAPI(authRegister);
        if (getResp?.output === "Success") {
            message.success("注册成功");
            message.info(`欢迎您 ${getResp.data!.user.username} 使用${webInfo.name}`);
            // 存储 Token 信息
            Cookies.set("Authorization", getResp.data!.token, {expires: 1});
            Cookies.set("X-User-UUID", getResp.data!.user.uuid, {expires: 1});
            // 跳转到登录
            setTimeout(() => {
                navigate("/dashboard/home");
            }, 1000);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    return (
        <animated.section style={prop} className="bg-white">
            <div className="lg:grid lg:min-h-screen lg:grid-cols-12">
                <section className="relative flex h-32 items-end bg-gray-900 lg:col-span-5 lg:h-full xl:col-span-6">
                    <img alt="backgroundImage"
                         src={backgroundImage}
                         className="absolute inset-0 h-full w-full object-cover opacity-80"/>
                    <div className="hidden lg:relative lg:block lg:p-12">
                        <Link className="block text-white" to="/">
                            <span className="sr-only">Home</span>
                            <LogoSVG/>
                        </Link>
                        <h2 className="mt-6 text-2xl font-bold text-white sm:text-3xl md:text-4xl">
                            欢迎来到{webInfo.name}
                        </h2>
                        <p className="mt-4 leading-relaxed text-white/90">
                            {webInfo.description}
                        </p>
                    </div>
                </section>
                <main
                    className="flex items-center justify-center px-8 py-8 sm:px-12 lg:col-span-7 lg:px-16 lg:py-12 xl:col-span-6">
                    <div className="max-w-xl lg:max-w-3xl">
                        <div className="relative -mt-16 block lg:hidden">
                            <Link to="/"
                                  className="inline-flex size-16 items-center justify-center rounded-full bg-white text-blue-600 sm:size-20">
                                <span className="sr-only">Home</span>
                                <LogoSVG/>
                            </Link>
                            <h1 className="mt-2 text-2xl font-bold text-gray-900 sm:text-3xl md:text-4xl">
                                欢迎来到{webInfo.name}
                            </h1>
                            <p className="mt-4 leading-relaxed text-gray-500">
                                {webInfo.description}
                            </p>
                        </div>

                        <form onSubmit={handleSubmit} className="mt-8 grid grid-cols-6 gap-6">
                            <RegisterInput type={"email"} name={"email"} display={"邮箱"}
                                           className={"col-span-6 sm:col-span-3"}
                                           mapping={(event) => {
                                               setAuthRegister({...authRegister, email: event.currentTarget.value});
                                           }}/>
                            <RegisterInput type={"text"} name={"phone"} display={"手机"}
                                           className={"col-span-6 sm:col-span-3"}
                                           mapping={(event) => {
                                               setAuthRegister({...authRegister, phone: event.currentTarget.value});
                                           }}/>
                            <RegisterInput type={"text"} name={"username"} display={"用户名"}
                                           className={"col-span-6"}
                                           mapping={(event) => {
                                               setAuthRegister({...authRegister, username: event.currentTarget.value});
                                           }}/>
                            <RegisterInput type={"password"} name={"password"} display={"密码"}
                                           className={"col-span-6 sm:col-span-3"}
                                           mapping={(event) => {
                                               setAuthRegister({...authRegister, password: event.currentTarget.value});
                                           }}/>
                            <RegisterInput type={"password"} name={"password_confirmation"} display={"确认密码"}
                                           className={"col-span-6 sm:col-span-3"}
                                           mapping={(event) => {
                                               confirmPassword.current = event.currentTarget.value;
                                           }}/>
                            <div className="col-span-6">
                                <p className="text-sm text-gray-500">
                                    <span>创建帐户，即表示您同意我们的 </span>
                                    <span onClick={() => {
                                    }} className="text-gray-700 underline">条款和条件</span>
                                    <span> 和 </span>
                                    <span onClick={() => {
                                    }} className="text-gray-700 underline">隐私策略</span>
                                    <span>.</span>
                                </p>
                            </div>
                            <div className="col-span-6 sm:flex sm:items-center sm:gap-4">
                                <button
                                    className="inline-block shrink-0 rounded-md border border-teal-600 bg-teal-600 px-12 py-3 text-sm font-medium text-white transition hover:bg-transparent hover:text-teal-600 focus:outline-none focus:ring active:text-teal-500">
                                    创建账号
                                </button>
                                <p className="mt-4 text-sm text-gray-500 sm:mt-0">
                                    <span>已有账号了吗? </span>
                                    <Link to="/auth/login"
                                          className="transition text-gray-700 hover:shadow-lg underline">登录</Link>.
                                </p>
                            </div>
                        </form>
                    </div>
                </main>
            </div>
        </animated.section>
    );
}
