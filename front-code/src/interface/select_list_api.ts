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
import {BaseApi, GetAuthorizationToken, MethodType} from "../assets/typescript/base_api.ts";
import {UserFriendEntity} from "../models/entity/user_friends_entity.ts";
import {UserInfoEntity} from "../models/entity/user_info_entity.ts";

/**
 * # 获取用户选择列表
 * 用于获取用户选择列表；该接口用于获取用户选择列表。
 * @param search 搜索内容
 *
 * @returns {Promise<BaseResponse<UserFriendEntity[]> | undefined>} 用户选择列表
 */
const SelectUserListAPI = (search: string): Promise<BaseResponse<UserInfoEntity[]> | undefined> => {
    return BaseApi<UserFriendEntity[]>(
        MethodType.GET,
        "/api/v1/select-list/user",
        null,
        {search: search},
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

export {
    SelectUserListAPI
}
