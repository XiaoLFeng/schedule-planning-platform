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

package com.xlf.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 项目启动类
 * <p>
 * 通过 {@link SpringApplication} 注解标注该类为 SpringBoot 项目启动类
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@EnableAsync
@EnableScheduling
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScans({
        @ComponentScan("com.xlf.schedule"),
        @ComponentScan(basePackageClasses = {
                com.xlf.utility.UtilProperties.class,
                com.xlf.utility.exception.PublicExceptionHandlerAbstract.class,
                com.xlf.utility.config.app.MybatisPlusConfiguration.class
        })
})
@EnableTransactionManagement
public class SchedulePlanningPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchedulePlanningPlatformApplication.class, args);
    }
}
