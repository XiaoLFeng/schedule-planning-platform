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
import {useEffect} from "react";
import {AuthLogin} from "./auth/auth_login.tsx";
import {AuthRegister} from "./auth/auth_register.tsx";

/**
 * # 基本认证页面
 * 用于认证的基本页面，包含登录、注册、找回密码等；
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
export function BaseAuth() {
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        if (location.pathname === "/auth") {
            navigate("/auth/login");
        }
    }, [location.pathname, navigate]);

    return (
        <div className={"min-h-dvh bg-gray-100/50"}>
            <Routes>
                <Route path={"/login"} element={<AuthLogin/>}/>
                <Route path={"/register"} element={<AuthRegister/>}/>
            </Routes>
        </div>
    );
}
