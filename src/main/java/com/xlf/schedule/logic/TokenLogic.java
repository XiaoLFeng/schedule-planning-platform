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

package com.xlf.schedule.logic;

import com.xlf.schedule.dao.TokenDAO;
import com.xlf.schedule.model.entity.TokenDO;
import com.xlf.schedule.service.TokenService;
import com.xlf.utility.util.UuidUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * 令牌逻辑
 * <p>
 * 该类用于定义令牌逻辑;
 * 该类使用 {@link Service} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Service
@RequiredArgsConstructor
public class TokenLogic implements TokenService {

    /**
     * 令牌数据访问对象
     */
    private final TokenDAO tokenDAO;

    @Override
    @Transactional
    public String createToken(String userUuid, Long expiredHourTime, HttpServletRequest request) {
        List<TokenDO> userTokenList = tokenDAO.lambdaQuery()
                .eq(TokenDO::getUserUuid, userUuid)
                .orderByAsc(TokenDO::getCreatedAt)
                .list();
        // 检查令牌是否超过 5 个
        if (userTokenList.size() > 5) {
            // 删除最早的令牌
            tokenDAO.lambdaUpdate()
                    .eq(TokenDO::getTokenUuid, userTokenList.get(0).getTokenUuid())
                    .remove();
        }
        // 创建新令牌
        TokenDO tokenDO = new TokenDO()
                .setTokenUuid(UuidUtil.generateStringUuid())
                .setUserUuid(userUuid)
                .setClientIp(request.getRemoteAddr())
                .setClientReferer(request.getHeader("Referer"))
                .setClientUserAgent(request.getHeader("User-Agent"))
                .setExpiredAt(new Timestamp(System.currentTimeMillis() + expiredHourTime * 3600 * 1000));
        tokenDAO.save(tokenDO);
        return tokenDO.getTokenUuid();
    }

    @Override
    @Transactional
    public boolean deleteToken(String token) {
        return tokenDAO.lambdaUpdate()
                .eq(TokenDO::getTokenUuid, token)
                .remove();
    }

    @Override
    @Transactional
    public boolean clearToken(String userUuid) {
        return tokenDAO.lambdaUpdate()
                .eq(TokenDO::getUserUuid, userUuid)
                .remove();
    }

    @Override
    @Transactional
    public boolean verifyToken(String token) {
        TokenDO tokenDO = tokenDAO.lambdaQuery()
                .eq(TokenDO::getTokenUuid, token)
                .one();
        if (tokenDO == null) {
            return false;
        }
        if (tokenDO.getExpiredAt().before(new Timestamp(System.currentTimeMillis()))) {
            tokenDAO.lambdaUpdate()
                    .eq(TokenDO::getTokenUuid, token)
                    .remove();
            return false;
        }
        return true;
    }

    @Override
    public List<TokenDO> list(String userUuid) {
        return tokenDAO.lambdaQuery()
                .eq(TokenDO::getUserUuid, userUuid)
                .list();
    }
}
