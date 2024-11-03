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
import {DashboardCurriculumHome} from "./curriculum/dashboard_curriculum_home.tsx";
import {DashboardCurriculumTime} from "./curriculum/dashboard_curriculum_time.tsx";

export function DashboardCurriculum({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {

    return (
        <Routes>
            <Route path={"/"} element={<DashboardCurriculumHome onHeaderHandler={onHeaderHandler}/>}/>
            <Route path={"/time"} element={<DashboardCurriculumTime onHeaderHandler={onHeaderHandler}/>}/>
        </Routes>
    );
}
