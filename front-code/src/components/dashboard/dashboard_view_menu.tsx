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
import {useRef} from "react";

export function DashboardViewMenu({to, text, className}: { to: string, text: string, className?: string }) {
    const getClazz = (): string => {
        if (location.pathname !== to) {
            return "transition flex gap-1 bg-sky-400 hover:bg-sky-500 text-white px-4 py-1.5 " + className;
        } else {
            return "transition flex gap-1 bg-sky-600 text-white px-4 py-1.5 " + className;
        }
    }

    const location = useLocation();
    const clazz = useRef<string>(getClazz());


    return (
        <Link to={to} className={"flex-shrink-0 col-span-2 flex justify-end"}>
            <div
                className={clazz.current}>
                <span>{text}</span>
            </div>
        </Link>
    );
}
