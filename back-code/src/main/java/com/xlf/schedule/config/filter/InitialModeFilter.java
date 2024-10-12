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

package com.xlf.schedule.config.filter;

import com.google.gson.Gson;
import com.xlf.schedule.constant.SystemConstant;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 初始模式过滤器
 * <p>
 * 该类用于配置初始模式过滤器;
 * 该类继承 {@link OncePerRequestFilter} 类;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
public class InitialModeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        Gson gson = new Gson();
        // 检查是否是初始化模式
        if ("true".equals(SystemConstant.isInitialMode)) {
            if (request.getRequestURI().contains("/api/v2/initial")) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write(
                        gson.toJson(
                                ResultUtil.error(ErrorCode.FORBIDDEN, "当前处于系统初始化模式", null)
                                        .getBody()
                        )
                );
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
