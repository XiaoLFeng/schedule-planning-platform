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
 * # 基本返回类型
 * 用于返回数据的基本类型，包含输出、状态码、消息、错误消息、数据；
 *
 * @template T 返回数据类型
 * @property {string} output 输出
 * @property {number} code 状态码
 * @property {string} message 消息
 * @property {string} errorMessage 错误消息
 * @property {T} data 数据
 */
export type BaseResponse<T> = {
    output: string;
    code: number;
    message: string;
    errorMessage?: string;
    data?: T;
}
