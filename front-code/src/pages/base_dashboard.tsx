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
import {DashboardHome} from "./dashboard/dashboard_home.tsx";
import {DashboardSideMenu} from "../components/dashboard/dashboard_side_menu.tsx";
import {useSelector} from "react-redux";
import {WebInfoEntity} from "../models/entity/web_info_entity.ts";

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

    return (
        <div className={""}>
            <div className={"min-h-dvh absolute left-0 top-0"}>
                <DashboardSideMenu webInfo={webInfo} />
            </div>
            <div className={"px-72 p-8"}>
                <Routes>
                    <Route path={"/home"} element={<DashboardHome/>}/>
                </Routes>
            </div>
        </div>
    );
}
