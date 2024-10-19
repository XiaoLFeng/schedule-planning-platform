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

import {FormEventHandler, HTMLInputTypeAttribute, useEffect} from "react";

/**
 * # 注册输入框
 * 用于注册页面的输入框，包含输入框的类型、名称、显示名称、样式类、映射事件。
 *
 * @param type 输入框类型
 * @param name 名字
 * @param display 显示名字
 * @param className 样式
 * @param mapping 映射事件
 * @constructor
 */
export function RegisterInput(
    {type, name, display, className, mapping}: {
        type: HTMLInputTypeAttribute,
        name: string,
        display: string,
        className?: string,
        mapping: FormEventHandler<HTMLInputElement>
    }
) {
    const getLabelName = name + "_label";

    useEffect(() => {
        if ((document.getElementById(name) as HTMLInputElement).value !== "") {
            document.getElementById(getLabelName)?.classList.remove("text-red-500");
            document.getElementById(name)?.classList.remove("border-red-500");
        }
    }, [getLabelName, mapping, name]);

    return (
        <div className={className}>
            <label htmlFor="Email" id={getLabelName} className="block text-sm font-medium text-gray-700">{display}</label>
            <input
                type={type}
                id={name}
                name={name}
                onInput={mapping}
                className="transition mt-1 w-full rounded-md border-gray-200 bg-white text-sm text-gray-700 shadow-sm"
            />
        </div>
    );
}
