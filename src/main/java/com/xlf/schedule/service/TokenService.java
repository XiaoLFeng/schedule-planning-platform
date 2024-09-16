package com.xlf.schedule.service;

import com.xlf.schedule.model.entity.TokenDO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 令牌服务
 * <p>
 * 该接口用于定义令牌服务;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
public interface TokenService {

    /**
     * 创建令牌
     * <p>
     * 该方法用于创建令牌。
     *
     * @param userUuid 用户UUID
     * @param expiredHourTime 有效时间（小时）
     * @param request 请求
     * @return {@link String} 令牌
     */
    String createToken(String userUuid, Long expiredHourTime, HttpServletRequest request);

    /**
     * 检查令牌
     * <p>
     * 该方法用于检查令牌。
     *
     * @param token 令牌
     * @return {@link Boolean} 是否有效
     */
    boolean deleteToken(String token);

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
