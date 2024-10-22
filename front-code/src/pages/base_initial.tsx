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

import {animated, easings, useSpring, useSprings} from "@react-spring/web";
import initBackground from "../assets/images/init_background.webp";
import {InputLabel} from "../components/input_label.tsx";
import {useEffect, useRef, useState} from "react";
import {InitDTO} from "../models/dto/init_dto.ts";
import {InitialAPI, IsInitAPI} from "../interface/init_api.ts";
import {message} from "antd";
import {useNavigate} from "react-router-dom";

export function BaseInitial() {
    const [initDTO, setInitDTO] = useState<InitDTO>({} as InitDTO);
    const navigate = useNavigate();

    const [springs] = useSprings(5, (index: number) => ({
        from: {opacity: 0, transform: "translateX(16px)", scale: 0.9},
        to: {opacity: 1, transform: "translateX(0)", scale: 1},
        config: {duration: 1000, easing: easings.easeInOutCubic},
        delay: 100 * (index + 1) + 500
    }));
    const prop = useSpring({
        from: {opacity: 0, scale: 0.8},
        to: {opacity: 1, scale: 1},
        config: {duration: 1000, easing: easings.easeInOutCubic},
        delay: 500
    });
    const image = useSpring({
        from: {opacity: 0, transform: "translateX(-16px)"},
        to: {opacity: 1, transform: "translateX(0)"},
        config: {duration: 1500, easing: easings.easeInOutCubic},
    });
    const initTimeout = useRef<number>(0);

    document.title = "系统初始化";

    useEffect(() => {
        const checkIsInitMode = async () => {
            if (!initTimeout.current) {
                initTimeout.current = setTimeout(async () => {
                    const getResp = await IsInitAPI();
                    if (getResp?.output === "Success") {
                        if (!getResp.data!) {
                            navigate("/");
                            message.warning("系统已初始化，请勿重复初始化");
                        }
                    }
                });
            }
        }
        checkIsInitMode().then();
    }, [navigate]);

    async function handlerSubmit() {
        // 数据检查
        if (initDTO.username === undefined || initDTO.username === "" ) {
            message.warning("请输入管理员用户名");
            return;
        }
        if (initDTO.email === undefined || initDTO.email === "") {
            message.warning("请输入管理员邮箱");
            return;
        }
        if (initDTO.phone === undefined || initDTO.phone === "") {
            message.warning("请输入管理员手机");
            return;
        }
        if (initDTO.password === undefined || initDTO.password === "") {
            message.warning("请输入管理员密码");
            return;
        }
        const getResp = await InitialAPI(initDTO);
        if (getResp?.output === "Success") {
            message.success("初始化成功，正在跳转到登录页面");
            setTimeout(() => {
                navigate("/");
            }, 1000);
        }
    }

    return (
        <div className="h-screen flex bg-gray-100/50">
            <animated.div style={image}
                          className="flex-1 grid justify-center items-center h-full w-full overflow-hidden">
                <img src={initBackground} alt="initBackground" className="object-none object-bottom opacity-80"/>
            </animated.div>
            <div className="flex-1 grid items-center">
                <div className={"grid gap-6 justify-center text-center"}>
                    <animated.div style={prop} className={"text-3xl font-extrabold"}>
                        学生日程管理系统 - 初始化
                    </animated.div>
                    <animated.div style={springs[0]} className={"text-left"}>
                        <InputLabel type={"text"} name={"username"} display={"管理员用户名"} mapping={
                            (event) => {
                                setInitDTO({...initDTO, username: event.currentTarget.value});
                            }
                        }/>
                    </animated.div>
                    <animated.div style={springs[1]} className={"text-left"}>
                        <InputLabel type={"email"} name={"email"} display={"管理员邮箱"} mapping={
                            (event) => {
                                setInitDTO({...initDTO, email: event.currentTarget.value});
                            }
                        }/>
                    </animated.div>
                    <animated.div style={springs[2]} className={"text-left"}>
                        <InputLabel type={"tel"} name={"phone"} display={"管理员手机"} mapping={
                            (event) => {
                                setInitDTO({...initDTO, phone: event.currentTarget.value});
                            }
                        }/>
                    </animated.div>
                    <animated.div style={springs[3]} className={"text-left"}>
                        <InputLabel type={"password"} name={"email"} display={"管理员密码"} mapping={
                            (event) => {
                                setInitDTO({...initDTO, password: event.currentTarget.value});
                            }
                        }/>
                    </animated.div>
                    <animated.div style={springs[4]} className={"flex justify-center"}>
                        <button type={"button"} className={"transition rounded-lg bg-teal-500 hover:bg-teal-600 text-white px-8 py-1.5"}
                             onClick={handlerSubmit}>
                            初始化
                        </button>
                    </animated.div>
                </div>
            </div>
        </div>
    );
}
