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

import com.google.gson.Gson;
import com.xlf.schedule.constant.MailConstant;
import com.xlf.schedule.constant.SystemConstant;
import com.xlf.schedule.constant.WebConstant;
import com.xlf.schedule.dao.InfoDAO;
import com.xlf.schedule.dao.RoleDAO;
import com.xlf.schedule.dao.TableDAO;
import com.xlf.schedule.model.entity.ClassTimeMarketDO;
import com.xlf.schedule.model.vo.ClassTimeVO;
import com.xlf.utility.util.UuidUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

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
    private final Environment environment;

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
        this.initClassDefaultCheck();
        this.initRole();
        this.initFolder();
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
            System.out.println("\t\t\t\u001B[33m::: " + SystemConstant.SYSTEM_AUTHOR + " :::\t\t\t\t\t\t\t ::: " + SystemConstant.SYSTEM_VERSION + " :::\u001B[0m");
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
        prepare.checkTable("xf_token");
        prepare.checkTable("xf_mail_code");
        prepare.checkTable("xf_group");
        prepare.checkTable("xf_group_member");
        prepare.checkTable("xf_friend");
        prepare.checkTable("xf_class_time_market");
        prepare.checkTable("xf_class_time_my");
        prepare.checkTable("xf_class_grade");
        prepare.checkTable("xf_class");
        prepare.checkTable("xf_schedule");
    }

    /**
     * 初始化 xf_info 表检查
     */
    private void initInfoTableCheck() {
        log.info("[INIT] 数据表 xf_info 缺陷检查...");

        // 基础配置
        prepare.checkInfoTableFields("system_initial_mode", "true");
        prepare.checkInfoTableFields("system_debug_mode", "true");
        prepare.checkInfoTableFields("system_super_admin_uuid", null);
        prepare.checkInfoTableFields("system_test_user_uuid", null);
        prepare.checkInfoTableFields("system_author", "xiao_lfeng");
        prepare.checkInfoTableFields("system_version", "v1.0.0");
        prepare.checkInfoTableFields("system_license", "MIT");
        prepare.checkInfoTableFields("system_copyright", "Copyright (c) 2016-2024 XiaoLFeng All rights reserved.");

        // Web 配置
        prepare.checkInfoTableFields("web_title", "学生日程规划平台");
        prepare.checkInfoTableFields("web_description", "学生日程规划平台旨在为学生提供一个全面、高效的日程管理工具，帮助他们合理规划学习与生活，提高时间管理能力。");
        prepare.checkInfoTableFields("web_keywords", "学生,日程,规划,平台,学习,生活,时间管理,课程表,空闲时间,好友,联系,自我管理,高效学习");
        prepare.checkInfoTableFields("web_icp", "粤ICP备2021000000号");
        prepare.checkInfoTableFields("web_record", "粤公网安备 31000002000001号");
    }

    /**
     * 初始化全局变量赋值
     */
    private void initGlobalVariableAssignment() {
        log.info("[INIT] 全局变量初始化...");

        // 系统配置
        SystemConstant.isInitialMode = prepare.initGetGlobalVariable("system_initial_mode");
        SystemConstant.isDebugMode = Boolean.parseBoolean(prepare.initGetGlobalVariable("system_debug_mode"));
        SystemConstant.superAdminUUID = prepare.initGetGlobalVariable("system_super_admin_uuid");
        SystemConstant.testUserUUID = prepare.initGetGlobalVariable("system_test_user_uuid");

        // 邮件配置
        MailConstant.mailUsername = environment.getProperty("xutil.mail.username");
        MailConstant.mailNickName = environment.getProperty("xutil.mail.nickname");
        MailConstant.mailDefaultEncoding = environment.getProperty("xutil.mail.default-encoding");

        // Web 配置
        WebConstant.name = prepare.initGetGlobalVariable("web_title");
        WebConstant.description = prepare.initGetGlobalVariable("web_description");
        WebConstant.keywords = prepare.initGetGlobalVariable("web_keywords");
        WebConstant.author = prepare.initGetGlobalVariable("system_author");
        WebConstant.version = prepare.initGetGlobalVariable("system_version");
        WebConstant.icp = prepare.initGetGlobalVariable("web_icp");
        WebConstant.record = prepare.initGetGlobalVariable("web_record");
        WebConstant.license = prepare.initGetGlobalVariable("system_license");
        WebConstant.copyright = prepare.initGetGlobalVariable("system_copyright");
    }

    /**
     * 初始化课表类型检查
     */
    private void initClassDefaultCheck() {
        log.info("[INIT] 检查默认课表时间信息...");
        if (prepare.initGetGlobalVariable("system_default_class_time_uuid") == null) {
            String newTimeMarketUuid = UuidUtil.generateUuidNoDash();
            ArrayList<ClassTimeVO.TimeAble> timeAble = new ArrayList<>();
            timeAble.add(new ClassTimeVO.TimeAble("08:00", "08:45"));
            timeAble.add(new ClassTimeVO.TimeAble("08:55", "09:40"));
            timeAble.add(new ClassTimeVO.TimeAble("10:10", "10:55"));
            timeAble.add(new ClassTimeVO.TimeAble("11:05", "11:50"));
            timeAble.add(new ClassTimeVO.TimeAble("13:45", "14:30"));
            timeAble.add(new ClassTimeVO.TimeAble("14:40", "15:25"));
            timeAble.add(new ClassTimeVO.TimeAble("15:55", "16:40"));
            timeAble.add(new ClassTimeVO.TimeAble("16:50", "17:35"));
            timeAble.add(new ClassTimeVO.TimeAble("18:45", "19:30"));
            timeAble.add(new ClassTimeVO.TimeAble("19:40", "20:25"));
            timeAble.add(new ClassTimeVO.TimeAble("20:35", "21:20"));
            ClassTimeMarketDO classTimeMarketDO = new ClassTimeMarketDO();
            classTimeMarketDO
                    .setClassTimeMarketUuid(newTimeMarketUuid)
                    .setName("默认课表时间")
                    .setTimetable(new Gson().toJson(timeAble))
                    .setIsPublic(true)
                    .setIsOfficial(true);
            jdbcTemplate.update("INSERT INTO xf_class_time_market (class_time_market_uuid, name, timetable, is_public, is_official) VALUES (?, ?, ?, ?, ?)",
                    classTimeMarketDO.getClassTimeMarketUuid(),
                    classTimeMarketDO.getName(),
                    classTimeMarketDO.getTimetable(),
                    classTimeMarketDO.getIsPublic(),
                    classTimeMarketDO.getIsOfficial()
            );
            prepare.checkInfoTableFields("system_default_class_time_uuid", newTimeMarketUuid);
            SystemConstant.defaultClassTimeUUID = newTimeMarketUuid;
        } else {
            SystemConstant.defaultClassTimeUUID = prepare.initGetGlobalVariable("system_default_class_time_uuid");
        }
    }

    /**
     * 初始化角色
     */
    private void initRole() {
        log.info("[INIT] 初始化角色...");

        prepare.initRole("ADMIN", "管理员", "拥有软件的所有权限，包括用户管理、角色管理、日志管理、信息管理等。");
        prepare.initRole("USER", "用户", "拥有软件的部分权限，包括日志查看、信息查看等。");
    }

    /**
     * 初始化文件夹
     */
    private void initFolder() {
        log.info("[INIT] 初始化文件夹...");

        prepare.initFolder("uploads");
        prepare.initFolder("uploads/images");
    }
}
