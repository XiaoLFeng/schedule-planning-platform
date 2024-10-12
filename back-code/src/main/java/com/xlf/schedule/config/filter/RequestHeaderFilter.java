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
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.exception.library.RequestHeaderNotMatchException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 请求头过滤器
 * <p>
 * 该类用于过滤请求头；
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
public class RequestHeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String[] noMatchUrls = {"/favicon.ico"};
        boolean isMatch = false;
        for (String noMatchUrl : noMatchUrls) {
            if (request.getRequestURI().contains(noMatchUrl)) {
                isMatch = true;
                break;
            }
        }
        try {
            if (!isMatch) {
                // 检查是否是空 Referer
                if (request.getHeader("Referer") == null || request.getHeader("Referer").isEmpty()) {
                    throw new RequestHeaderNotMatchException("请求缺少 Referer 头");
                }
                // 检查请求头是否包含正确的 Content-Type
                if (request.getContentType() == null) {
                    throw new RequestHeaderNotMatchException("Content-Type 不能为空");
                }
                // 检查请求头是否包含正确的 User-Agent
                if (request.getHeader("User-Agent") == null || request.getHeader("User-Agent").isEmpty()) {
                    throw new RequestHeaderNotMatchException("请求头中缺少 User-Agent");
                }
            }
            filterChain.doFilter(request, response);
        } catch (RequestHeaderNotMatchException e) {
            Gson gson = new Gson();
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    gson.toJson(
                            ResultUtil.error(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage(), null).getBody()
                    )
            );
        }
    }
}
