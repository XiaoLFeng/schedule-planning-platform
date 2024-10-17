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

package com.xlf.schedule.config.aspect;

import com.xlf.utility.aspect.BusinessLogAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 * <p>
 * 该类用于定义日志切面;
 * 该类使用 {@link Aspect} 注解标记;
 * 该类使用 {@link Component} 注解标记;
 * 该类继承 {@link BusinessLogAspect} 类;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Aspect
@Component
public class LogAspect extends BusinessLogAspect {
    @Override
    @Before("execution(* com.xlf.schedule.controller..*.*(..))")
    public void beforeControllerLog(@NotNull JoinPoint joinPoint) {
        super.beforeControllerLog(joinPoint);
    }

    @Override
    @Before("execution(* com.xlf.schedule.service..*.*(..))")
    public void beforeServiceLog(@NotNull JoinPoint joinPoint) {
        super.beforeServiceLog(joinPoint);
    }

    @Override
    @Around("execution(* com.xlf.schedule.dao..*.*(..))")
    public Object beforeDaoLog(@NotNull ProceedingJoinPoint pjp) throws Throwable {
        return super.beforeDaoLog(pjp);
    }
}
