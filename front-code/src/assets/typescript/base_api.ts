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

import axios, {type AxiosResponse} from "axios";
import type {BaseResponse} from "../../models/base_response";
import Cookies from "js-cookie";

const BASE_API_URL: string = import.meta.env.VITE_APP_API_URL;

/**
 * Http 方法类型枚举
 */
enum MethodType {
    GET = "GET",
    POST = "POST",
    PUT = "PUT",
    DELETE = "DELETE",
    OPTIONS = "OPTIONS",
    HEAD = "HEAD",
}

/**
 * # 基础请求方法
 * - 这是一个基础的请求方法，用于发送请求，返回 Promise 对象；简化了 axios 的使用，减少了重复代码；
 * - 其他接口在进行调用的时候，可以直接调用这个方法，传入对应的参数即可；若出现报错内容，此方法直接执行报错内容输出；
 * - 对于可选的内容，若不需要，可以传入 null，不会影响请求；请不要传递空字符串，将会以空字符串进行请求；而不是不传递；
 *
 * @param method 请求方法
 * @param url 请求地址(不包含基础地址)
 * @param bodyData 请求体数据(选)
 * @param paramData 请求参数数据(选)
 * @param pathData 请求路径数据(选)
 * @param headers 请求头数据(选)
 * @constructor 返回 Promise 对象
 * @returns Promise 对象
 */
async function BaseApi<E>(
    method: MethodType,
    url: string,
    bodyData: Record<string, unknown> | null,
    paramData: Record<string, unknown> | null,
    pathData: string | null,
    headers: Record<string, string> | null
): Promise<BaseResponse<E> | undefined> {
    return axios({
        method: method,
        url: makeURL(url, pathData),
        data: makeData(bodyData),
        params: paramData,
        headers: pushHeader(headers)
    }).then((response: AxiosResponse<BaseResponse<E>, object>) => {
        return response.data;
    }).catch((error) => {
        console.error("[API] 请求出现问题", error);
        const getResponse: BaseResponse<E> = error.response.data!;
        if (getResponse) {
            return getResponse;
        }
    }).finally(() => {
        console.log("[API] 请求 [" + method + "] " + makeURL(url, pathData) + " 接口");
    });
}

/**
 * # 拼接 URL
 * 拼接 URL，将基础地址和请求地址拼接在一起，返回拼接后的地址；
 * 若有路径数据，则拼接路径数据；若没有路径数据，则不拼接路径数据；
 *
 * @param url 请求地址
 * @param pathData 路径数据
 * @returns 拼接后的地址
 */
const makeURL = (url: string, pathData: string | null): string => {
    if (pathData) {
        return `${BASE_API_URL}${url}/${pathData}`;
    } else {
        return `${BASE_API_URL}${url}`;
    }

}

/**
 * # 处理数据
 * 处理数据，将数据进行处理，若数据存在，则返回数据；若数据不存在，则返回 null；
 *
 * @param data 数据
 * @returns 处理后的数据
 */
const makeData = (data: unknown): unknown => {
    if (data) {
        return data;
    } else {
        return null;
    }
}

const pushHeader = (headers: Record<string, string> | null): Record<string, string> => {
    if (headers) {
        headers["Content-Type"] = "application/json";
        return headers;
    } else {
        return {
            "Content-Type": "application/json"
        }
    }
}

function GetAuthorizationToken(): string {
    const token = Cookies.get("authorization-token");
    if (token) {
        return "Bearer " + token;
    } else {
        return ""
    }
}

export {BaseApi, MethodType, GetAuthorizationToken};
