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

package com.xlf.schedule.config.init;

import com.xlf.schedule.constant.SystemConstant;
import com.xlf.schedule.dao.InfoDAO;
import com.xlf.schedule.dao.RoleDAO;
import com.xlf.schedule.dao.TableDAO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 初始化类
 * <p>
 * 该类用于初始化项目;
 * 该类使用 {@link Configuration} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class Initialize {
    private final TableDAO tableDAO;
    private final InfoDAO infoDAO;
    private final RoleDAO roleDAO;
    private final JdbcTemplate jdbcTemplate;

    private PrepareAlgorithm prepare;

    /**
     * 初始化项目
     */
    @PostConstruct
    public void init() {
        log.info("[INIT] 系统初始化开始");
        log.info("========== Start of Initialization ==========");
        // 初始化准备算法
        prepare = new PrepareAlgorithm(tableDAO, infoDAO, roleDAO, jdbcTemplate);

        // 初始化数据库完整性检查
        this.initSqlCheck();
        this.initInfoTableCheck();
        this.initGlobalVariableAssignment();
        this.initRole();
    }

    /**
     * 初始化结束
     *
     * @return {@link CommandLineRunner} 命令行运行器
     */
    @Bean
    public CommandLineRunner initFinal() {
        return args -> {
            log.info("=========== End of Initialization ===========");
            System.out.print("""
                    \u001B[38;5;111m   _____      __             __      __        \u001B[32m____  __                  _           \s
                    \u001B[38;5;111m  / ___/_____/ /_  ___  ____/ /_  __/ /__     \u001B[32m/ __ \\/ /___ _____  ____  (_)___  ____ _
                    \u001B[38;5;111m  \\__ \\/ ___/ __ \\/ _ \\/ __  / / / / / _ \\   \u001B[32m/ /_/ / / __ `/ __ \\/ __ \\/ / __ \\/ __ `/
                    \u001B[38;5;111m ___/ / /__/ / / /  __/ /_/ / /_/ / /  __/  \u001B[32m/ ____/ / /_/ / / / / / / / / / / / /_/ /\s
                    \u001B[38;5;111m/____/\\___/_/ /_/\\___/\\__,_/\\__,_/_/\\___/\u001B[32m  /_/   /_/\\__,_/_/ /_/_/ /_/_/_/ /_/\\__, / \s
                                                                                   \u001B[32m              /____/  \s
                    """);
            System.out.println("\t\t\t\u001B[33m::: " + SystemConstant.SYSTEM_AUTHOR + " :::\t\t\t\t\t\t\t ::: "+ SystemConstant.SYSTEM_VERSION +" :::\u001B[0m");
        };
    }

    /**
     * 初始化数据库完整性检查
     */
    private void initSqlCheck() {
        log.info("[INIT] 数据库完整性检查...");

        prepare.checkTable("xf_info");
        prepare.checkTable("xf_logs");
        prepare.checkTable("xf_role");
        prepare.checkTable("xf_user");
    }

    /**
     * 初始化 xf_info 表检查
     */
    private void initInfoTableCheck() {
        log.info("[INIT] 数据表 xf_info 缺陷检查...");

        prepare.checkInfoTableFields("system_initial_mode", "true");
        prepare.checkInfoTableFields("system_debug_mode", "true");
        prepare.checkInfoTableFields("system_super_admin_uuid", null);
        prepare.checkInfoTableFields("system_test_user_uuid", null);
    }

    /**
     * 初始化全局变量赋值
     */
    private void initGlobalVariableAssignment() {
        log.info("[INIT] 全局变量初始化...");

        SystemConstant.isInitialMode = prepare.initGetGlobalVariable("system_initial_mode");
        SystemConstant.isDebugMode = Boolean.parseBoolean(prepare.initGetGlobalVariable("system_debug_mode"));
        SystemConstant.superAdminUUID = prepare.initGetGlobalVariable("system_super_admin_uuid");
        SystemConstant.testUserUUID = prepare.initGetGlobalVariable("system_test_user_uuid");
    }

    /**
     * 初始化角色
     */
    private void initRole() {
        log.info("[INIT] 初始化角色...");

        prepare.initRole("ADMIN", "管理员", "拥有软件的所有权限，包括用户管理、角色管理、日志管理、信息管理等。");
        prepare.initRole("USER", "用户", "拥有软件的部分权限，包括日志查看、信息查看等。");
    }
}
