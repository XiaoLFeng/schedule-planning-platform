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
import {ScheduleGroupListDTO} from "../models/dto/schedule_group_list_dto.ts";
import {Page} from "../models/page.ts";
import {ScheduleGroupEntity} from "../models/entity/schedule_group_entity.ts";

/**
 * # 获取课程表分组
 * 用于获取课程表分组；该接口用于获取课程表分组。
 *
 * @param paramData {ScheduleGroupListDTO} 课程表分组
 * @returns {Promise<BaseResponse<void> | undefined>} 课程表分组
 */
const GetScheduleGroupAPI = (paramData: ScheduleGroupListDTO): Promise<BaseResponse<Page<ScheduleGroupEntity>> | undefined> => {
    return BaseApi<Page<ScheduleGroupEntity>>(
        MethodType.GET,
        "/api/v1/schedule/group",
        null,
        paramData,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

export {
    GetScheduleGroupAPI
}
