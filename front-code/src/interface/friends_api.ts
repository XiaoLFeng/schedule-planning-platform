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
import {FriendAddDTO} from "../models/dto/friend_add_dto.ts";

/**
 * # 获取用户好友列表
 * 用于获取用户好友列表；该接口用于获取用户好友列表。
 *
 * @returns {Promise<BaseResponse<UserFriendEntity[]> | undefined>} 用户好友列表
 */
const GetUserFriendsListAPI = (): Promise<BaseResponse<UserFriendEntity[]> | undefined> => {
    return BaseApi<UserFriendEntity[]>(
        MethodType.GET,
        "/api/v1/friend/list",
        null,
        null,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 获取好友申请列表
 * 用于获取好友申请列表；该接口用于获取好友申请列表。
 *
 * @returns {Promise<BaseResponse<UserFriendEntity[]> | undefined>} 好友申请列表
 */
const GetFriendApplicationAPI = (): Promise<BaseResponse<UserFriendEntity[]> | undefined> => {
    return BaseApi<UserFriendEntity[]>(
        MethodType.GET,
        "/api/v1/friend/application",
        null,
        null,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 获取好友待处理列表
 * 用于获取好友待处理列表；该接口用于获取好友待处理列表。
 *
 * @returns {Promise<BaseResponse<UserFriendEntity[]> | undefined>} 好友待处理列表
 */
const GetFriendPendingAPI = (): Promise<BaseResponse<UserFriendEntity[]> | undefined> => {
    return BaseApi<UserFriendEntity[]>(
        MethodType.GET,
        "/api/v1/friend/pending",
        null,
        null,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 获取好友允许列表
 * 用于获取好友允许列表；该接口用于获取好友允许列表。
 *
 * @returns {Promise<BaseResponse<UserFriendEntity[]> | undefined>} 好友允许列表
 */
const GetFriendAllowAPI = (): Promise<BaseResponse<UserFriendEntity[]> | undefined> => {
    return BaseApi<UserFriendEntity[]>(
        MethodType.GET,
        "/api/v1/friend/pending",
        null,
        null,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 添加好友
 * 用于添加好友；该接口用于添加好友。
 *
 * @param paramData 添加好友参数
 * @returns {Promise<BaseResponse<void> | undefined>} 添加好友结果
 */
const AddUserAPI = (paramData: FriendAddDTO): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.POST,
        "/api/v1/friend/",
        null,
        paramData,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 同意添加用户
 * 用于同意添加用户；该接口用于同意添加用户。
 *
 * @param paramData 添加好友参数
 * @returns {Promise<BaseResponse<void> | undefined>} 同意添加用户结果
 */
const AllowUserAPI = (paramData: FriendAddDTO): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.GET,
        "/api/v1/friend/allow",
        null,
        paramData,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 删除用户
 * 用于删除用户；该接口用于删除用户。
 *
 * @param paramData 用户UUID
 * @returns {Promise<BaseResponse<void> | undefined>} 删除用户结果
 */
const DeleteUserAPI = (paramData: string): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.DELETE,
        "/api/v1/friend/",
        null,
        {"friend_uuid": paramData},
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 获取好友拒绝列表
 * 用于获取好友拒绝列表；该接口用于获取好友拒绝列表。
 *
 * @returns {Promise<BaseResponse<UserFriendEntity[]> | undefined>} 好友拒绝列表
 */
const GetFriendDeniedAPI = (): Promise<BaseResponse<UserFriendEntity[]> | undefined> => {
    return BaseApi<UserFriendEntity[]>(
        MethodType.GET,
        "/api/v1/friend/denied",
        null,
        null,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

export {
    GetUserFriendsListAPI,
    GetFriendApplicationAPI,
    GetFriendPendingAPI,
    GetFriendAllowAPI,
    AddUserAPI,
    AllowUserAPI,
    DeleteUserAPI,
    GetFriendDeniedAPI
}
