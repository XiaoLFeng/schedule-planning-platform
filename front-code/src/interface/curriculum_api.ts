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
import {ClassGradeDTO} from "../models/dto/class_grade_create_dto.ts";
import {ClassGradeEntity} from "../models/entity/class_grade_entity.ts";
import {ClassDTO} from "../models/dto/class_dto.ts";
import {ClassMutiMoveDTO} from "../models/dto/class_muti_move_dto.ts";
import {ClassMutiDeleteDTO} from "../models/dto/class_muti_delete_dto.ts";
import {Page} from "../models/page.ts";
import {PageDTO} from "../models/dto/page_dto.ts";
import {ClassTimeEntity} from "../models/entity/class_time_entity.ts";
import {TimeAddDTO} from "../models/dto/time_add_dto.ts";

/**
 * # 创建课程表
 * 用于创建课程表；该接口用于创建课程表。
 *
 * @param bodyData {ClassGradeDTO} 创建课程表
 * @constructor
 */
const CreateClassGradeAPI = (bodyData: ClassGradeDTO): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.POST,
        "/api/v1/curriculum/grade",
        bodyData,
        null,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 获取课程表
 * 用于获取课程表；该接口用于获取课程表。
 *
 * @param pathData {string} 获取课程表
 * @returns {Promise<BaseResponse<void> | undefined>} 课程表
 */
const GetClassGradeAPI = (pathData: string): Promise<BaseResponse<ClassGradeEntity> | undefined> => {
    return BaseApi<ClassGradeEntity>(
        MethodType.GET,
        "/api/v1/curriculum/grade",
        null,
        null,
        pathData,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 编辑课程表
 * 用于编辑课程表；该接口用于编辑课程表。
 *
 * @param pathData {string} 编辑课程表
 * @param bodyData {ClassGradeDTO} 编辑课程表
 * @returns {Promise<BaseResponse<void> | undefined>} 编辑课程表
 */
const EditClassGradeAPI = (pathData: string, bodyData: ClassGradeDTO): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.PUT,
        "/api/v1/curriculum/grade",
        bodyData,
        null,
        pathData,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 删除课程表
 * 用于删除课程表；该接口用于删除课程表。
 *
 * @param pathData {string} 删除课程表
 * @returns {Promise<BaseResponse<void> | undefined>} 删除课程表
 */
const DeleteClassGradeAPI = (pathData: string): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.DELETE,
        "/api/v1/curriculum/grade",
        null,
        null,
        pathData,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 添加课程表
 * 用于添加课程表；该接口用于添加课程表。
 *
 * @param bodyData {ClassGradeDTO} 添加课程表
 * @returns {Promise<BaseResponse<void> | undefined>} 添加课程表
 */
const AddClassAPI = (bodyData: ClassDTO): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.POST,
        "/api/v1/curriculum/class",
        bodyData,
        null,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 移动多个课程表
 * 用于移动多个课程表；该接口用于移动多个课程表。
 *
 * @param paramBody {ClassMutiMoveDTO} 移动多个课程表
 * @returns {Promise<BaseResponse<void> | undefined>} 移动多个课程表
 */
const MoveMutiClassAPI = (paramBody: ClassMutiMoveDTO): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.PUT,
        "/api/v1/curriculum/class",
        null,
        paramBody,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 删除多个课程表
 * 用于删除多个课程表；该接口用于删除多个课程表。
 *
 * @param paramBody {ClassMutiDeleteDTO} 删除多个课程表
 * @returns {Promise<BaseResponse<void> | undefined>} 删除多个课程表
 */
const DeleteMutiClassAPI = (paramBody: ClassMutiDeleteDTO): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.DELETE,
        "/api/v1/curriculum/class",
        null,
        paramBody,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 获取课程时间市场
 * 用于获取课程时间市场；该接口用于获取课程时间市场。
 *
 * @param paramData {PageDTO} 获取课程时间市场
 * @returns {Promise<BaseResponse<Page<ClassTimeEntity>> | undefined>} 课程时间市场
 */
const GetClassTimeMarketAPI = (paramData: PageDTO): Promise<BaseResponse<Page<ClassTimeEntity>> | undefined> => {
    return BaseApi<Page<ClassTimeEntity>>(
        MethodType.GET,
        "/api/v1/curriculum/time",
        null,
        paramData,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 获取我的课程时间
 * 用于获取我的课程时间；该接口用于获取我的课程时间。
 *
 * @param paramData {PageDTO} 获取我的课程时间
 * @returns {Promise<BaseResponse<Page<ClassTimeEntity>> | undefined>} 我的课程时间
 */
const GetClassMyTimeAPI = (paramData: PageDTO): Promise<BaseResponse<Page<ClassTimeEntity>> | undefined> => {
    return BaseApi<Page<ClassTimeEntity>>(
        MethodType.GET,
        "/api/v1/curriculum/my-time",
        null,
        paramData,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 添加课程时间
 * 用于添加课程时间；该接口用于添加课程时间。
 *
 * @param bodyData {ClassTimeEntity} 添加课程时间
 * @returns {Promise<BaseResponse<void> | undefined>} 添加课程时间
 */
const AddClassTimeAPI = (bodyData: TimeAddDTO): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.POST,
        "/api/v1/curriculum/time",
        bodyData,
        null,
        null,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 删除课程时间
 * 用于删除课程时间；该接口用于删除课程时间。
 *
 * @param pathData {string} 删除课程时间
 * @returns {Promise<BaseResponse<void> | undefined>} 删除课程时间
 */
const DelClassTimeAPI = (pathData: string): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.DELETE,
        "/api/v1/curriculum/time",
        null,
        null,
        pathData,
        {Authorization: GetAuthorizationToken()}
    );
}

/**
 * # 删除我的课程时间
 * 用于删除我的课程时间；该接口用于删除我的课程时间。
 *
 * @param pathData {string} 删除我的课程时间
 * @returns {Promise<BaseResponse<void> | undefined>} 删除我的课程时间
 */
const DelMyClassTimeAPI = (pathData: string): Promise<BaseResponse<void> | undefined> => {
    return BaseApi<void>(
        MethodType.DELETE,
        "/api/v1/curriculum/my-time",
        null,
        null,
        pathData,
        {Authorization: GetAuthorizationToken()}
    );
}

export {
    CreateClassGradeAPI,
    GetClassGradeAPI,
    EditClassGradeAPI,
    DeleteClassGradeAPI,
    AddClassAPI,
    MoveMutiClassAPI,
    DeleteMutiClassAPI,
    GetClassTimeMarketAPI,
    GetClassMyTimeAPI,
    AddClassTimeAPI,
    DelClassTimeAPI,
    DelMyClassTimeAPI
}
