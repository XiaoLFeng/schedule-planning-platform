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

package com.xlf.schedule.service;

import com.xlf.schedule.model.entity.TokenDO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 令牌服务
 * <p>
 * 该接口用于定义令牌服务;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
public interface TokenService {

    /**
     * 创建令牌
     * <p>
     * 该方法用于创建令牌。
     *
     * @param userUuid        用户UUID
     * @param expiredHourTime 有效时间（小时）
     * @param request         请求
     * @return {@link String} 令牌
     */
    String createToken(String userUuid, Long expiredHourTime, HttpServletRequest request);

    /**
     * 检查令牌
     * <p>
     * 该方法用于检查令牌。
     *
     * @param token   令牌
     * @param request 请求
     */
    void deleteToken(String token, HttpServletRequest request);

    /**
     * 删除令牌
     * <p>
     * 该方法用于删除令牌。
     *
     * @param request 请求
     */
    void deleteTokenByRequest(HttpServletRequest request);

    /**
     * 清除令牌
     * <p>
     * 该方法用于清除令牌，即删除令牌；
     * 当执行该方法时，将删除指定用户的所有令牌，即清除所有令牌。
     *
     * @param userUuid 用户UUID
     * @return {@link Boolean} 是否成功
     */
    boolean clearToken(String userUuid);

    /**
     * 验证令牌
     * <p>
     * 该方法用于验证令牌，令牌有效则返回 true，否则返回 false；
     * 令牌有效即指令令牌存在且未过期；
     * 令牌如果过期，将会删除该令牌。
     *
     * @param token 令牌
     * @return {@link Boolean} 是否有效
     */
    boolean verifyToken(String token);

    /**
     * 列出令牌
     * <p>
     * 该方法用于列出令牌。
     *
     * @param userUuid 用户UUID
     * @return {@link List<TokenDO>} 令牌列表
     */
    List<TokenDO> list(String userUuid);
}
