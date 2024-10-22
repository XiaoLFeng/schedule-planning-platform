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

package com.xlf.schedule.task;

import com.xlf.schedule.constant.SystemConstant;
import com.xlf.schedule.dao.TokenDAO;
import com.xlf.schedule.model.entity.TokenDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Token 过期检查任务
 * <p>
 * 该类用于定义 Token 过期检查任务;
 * 该类使用 {@link Component} 注解标记;
 * 该类使用 {@link RequiredArgsConstructor} 注解标记;
 * 该类使用 {@link Scheduled} 注解标记;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenExpiredCheckTask {

    private final TokenDAO tokenDAO;

    @Scheduled(cron = "0 0 */4 * * *")
    public void checkTokenExpired() {
        log.info("[CRON] Token 过期检查任务执行");
        long startTime = System.currentTimeMillis();
        if (SystemConstant.isDebugMode) {
            StringBuilder builder = new StringBuilder("[CRON] Token 过期数据\n\t");
            try {
                List<TokenDO> expiredTokenList = tokenDAO.lambdaQuery()
                        .lt(TokenDO::getExpiredAt, new Timestamp(System.currentTimeMillis()))
                        .list();
                expiredTokenList.forEach(token -> {
                    builder.append("> ").append("[").append(token.getTokenUuid()).append("]")
                            .append(" ").append(token.getExpiredAt()).append("\n\t");
                    tokenDAO.lambdaUpdate().eq(TokenDO::getTokenUuid, token.getTokenUuid()).remove();
                });
                log.debug("[CRON] Token 过期检查完成，共清理 {} 条过期 Token", expiredTokenList.size());
                log.debug(builder.toString());
            } catch (Exception e) {
                log.error("[CRON] Token 过期检查任务执行失败", e);
                return;
            }
        } else {
            tokenDAO.lambdaUpdate().lt(TokenDO::getExpiredAt, new Timestamp(System.currentTimeMillis())).remove();
        }
        long endTime = System.currentTimeMillis();
        log.info("[CRON] Token 过期检查完成，耗时: {}ms", endTime - startTime);
    }
}
