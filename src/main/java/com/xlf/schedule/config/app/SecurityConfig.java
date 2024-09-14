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

package com.xlf.schedule.config.app;

import com.xlf.schedule.config.filter.InitialModeFilter;
import com.xlf.schedule.config.filter.RequestHeaderFilter;
import com.xlf.utility.config.filter.AllowOptionFilter;
import com.xlf.utility.config.filter.CorsAllAllowFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security 配置类
 * <p>
 * 该类用于配置 Security 相关配置;
 * 该类使用 {@link Configuration} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Configuration
public class SecurityConfig {

    /**
     * 配置 Security 过滤器链
     *
     * @param security 对象
     * @return {@link SecurityFilterChain} 对象
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity security) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(new RequestHeaderFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CorsAllAllowFilter(), RequestHeaderFilter.class)
                .addFilterBefore(new AllowOptionFilter(), CorsAllAllowFilter.class)
                .addFilterBefore(new InitialModeFilter(), AllowOptionFilter.class)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().permitAll()
                )
                .build();
    }
}
