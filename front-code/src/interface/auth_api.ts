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

import {BaseApi, MethodType} from "../assets/typescript/base_api.ts";
import {AuthUserEntity} from "../models/entity/auth_user_entity.ts";
import {AuthRegisterDTO} from "../models/dto/auth_register_dto.tsx";
import {BaseResponse} from "../models/base_response.ts";

/**
 * # 用户注册
 * 用户注册，包含用户信息和令牌；该接口用于用户注册，注册成功后返回用户信息和令牌。
 *
 * @param getData 注册数据
 * @returns {Promise<BaseResponse<AuthUserEntity> | undefined>} 用户信息和令牌
 */
const AuthRegisterAPI = (getData: AuthRegisterDTO): Promise<BaseResponse<AuthUserEntity> | undefined> => {
    return BaseApi<AuthUserEntity>(
        MethodType.POST,
        "/api/v1/auth/register",
        getData,
        null,
        null,
        null
    );
}

export {
    AuthRegisterAPI
}
