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

import {BaseApi, GetAuthorizationToken, MethodType} from "../assets/typescript/base_api.ts";
import {UserEntity} from "../models/entity/user_entity.ts";
import {BaseResponse} from "../models/base_response.ts";

/**
 * # 当前用户
 * 获取当前登录用户的信息；该接口用于获取当前登录用户的信息。
 *
 * @param userUuid 用户 UUID
 * @returns {Promise<BaseResponse<AuthUserEntity> | undefined>} 用户信息
 */
const UserCurrentAPI = (userUuid: string): Promise<BaseResponse<UserEntity> | undefined> => {
    return BaseApi<UserEntity>(
        MethodType.GET,
        "/api/v1/user/current/",
        null,
        null,
        userUuid,
        {Authorization: GetAuthorizationToken()}
    );
}

export {
    UserCurrentAPI
}
