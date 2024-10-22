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

import {BaseResponse} from "../models/base_response.ts";
import {BaseApi, MethodType} from "../assets/typescript/base_api.ts";
import {InitDTO} from "../models/dto/init_dto.ts";

/**
 * # 是否初始化
 * 用于判断系统是否已经初始化；该接口用于判断系统是否已经初始化。
 *
 * @returns {Promise<BaseResponse<boolean> | undefined>} 是否初始化
 */
const IsInitAPI = (): Promise<BaseResponse<boolean> | undefined> => {
    return BaseApi<boolean>(
        MethodType.GET,
        "/api/v2/initial/is-initiated",
        null,
        null,
        null,
        null
    );
}

/**
 * # 初始化
 * 用于初始化系统；该接口用于初始化系统。
 *
 * @param bodyData 初始化数据
 * @returns {Promise<BaseResponse<void> | undefined>} 无返回
 */
const InitialAPI = (bodyData: InitDTO): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.POST,
        "/api/v2/initial/set-up",
        bodyData,
        null,
        null,
        null
    );
}

export {
    IsInitAPI,
    InitialAPI
};
