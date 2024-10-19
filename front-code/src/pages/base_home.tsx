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

import {Route, Routes} from "react-router-dom";
import {HomeIndex} from "./home/home_index.tsx";

/**
 * # 基本主页
 * 用于主页的基本页面；用于显示这个站点的主页；有什么作用以及新的功能。
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
export function BaseHome() {

    return (
        <Routes>
            <Route path={"/"} element={<HomeIndex/>}/>
        </Routes>
    )
}
