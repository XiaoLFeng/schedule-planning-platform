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

/**
 * # 用户实体
 * 用户实体，包含用户邮箱、用户是否开启、手机号、角色、用户名、用户表主键、封禁到、创建时间、修改时间。
 */
export type UserEntity = {
    /**
     * 封禁到
     */
    bannedAt: number;
    /**
     * 创建时间
     */
    createdAt: number;
    /**
     * 用户邮箱
     */
    email: string;
    /**
     * 用户是否开启
     */
    enable: boolean;
    /**
     * 手机号
     */
    phone: string;
    /**
     * 角色
     */
    role: string;
    /**
     * 修改时间
     */
    updatedAt: number;
    /**
     * 用户名
     */
    username: string;
    /**
     * 用户表主键
     */
    uuid: string;
}
