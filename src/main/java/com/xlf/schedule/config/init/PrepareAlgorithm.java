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

import com.xlf.schedule.dao.InfoDAO;
import com.xlf.schedule.dao.RoleDAO;
import com.xlf.schedule.dao.TableDAO;
import com.xlf.schedule.model.entity.InfoDO;
import com.xlf.schedule.model.entity.RoleDO;
import com.xlf.schedule.model.entity.TableDO;
import com.xlf.utility.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 准备算法
 * <p>
 * 该类用于准备算法;
 * 该类使用 {@link PrepareAlgorithm} 注解标记;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Slf4j
@RequiredArgsConstructor
public class PrepareAlgorithm {
    private final TableDAO tableDAO;
    private final InfoDAO infoDAO;
    private final RoleDAO roleDAO;
    private final JdbcTemplate jdbcTemplate;

    /**
     * 检查表
     * <p>
     * 该方法用于检查表；当表不存在时，创建表；若表存在，忽略数据库的检查。
     *
     * @param tableName 表名
     */
    public void checkTable(String tableName) {
        TableDO tableDO = tableDAO.lambdaQuery().eq(TableDO::getTableName, tableName).one();
        if (tableDO == null) {
            ClassPathResource classPathResource = new ClassPathResource("/templates/sql/" + tableName + ".sql");
            try {
                String getSql = FileCopyUtils.copyToString(new InputStreamReader(classPathResource.getInputStream(), StandardCharsets.UTF_8));
                getSql = getSql.replaceAll("(?s)/\\*.*?\\*/", "");
                for (String sql : getSql.split(";")) {
                    jdbcTemplate.execute(sql);
                }
                log.debug("[INIT] 创建表 | {}", tableName);
            } catch (IOException e) {
                log.error("[INIT] 读取SQL文件失败 | {}", e.getMessage(), e);
            }
        }
    }

    /**
     * 检查信息表字段
     * <p>
     * 该方法用于检查信息表字段，当字段不存在时，创建字段；若字段存在，忽略数据库的检查。
     *
     * @param key 键
     * @param value 值
     */
    public void checkInfoTableFields(String key, String value) {
        InfoDO infoDO = infoDAO.lambdaQuery().eq(InfoDO::getKey, key).one();
        if (infoDO == null) {
            InfoDO newInfo = new InfoDO()
                    .setKey(key)
                    .setValue(value);
            infoDAO.save(newInfo);
            log.debug("[INIT] 创建信息表字段 | <{}>{}", key, value);
        }
    }

    /**
     * 获取全局变量
     * <p>
     * 该方法用于获取全局变量；当全局变量不存在时，初始化全局变量。
     */
    public String initGetGlobalVariable(String key) {
        InfoDO infoDO = infoDAO.lambdaQuery().eq(InfoDO::getKey, key).one();
        if (infoDO == null) {
            return null;
        }
        return infoDO.getValue();
    }

    /**
     * 初始化全局变量赋值
     * <p>
     * 该方法用于初始化全局变量赋值；当全局变量不存在时，初始化全局变量。
     */
    public void initRole(String name, String displayName, String desc) {
        RoleDO roleDO = roleDAO.lambdaQuery().eq(RoleDO::getName, name).one();
        if (roleDO == null) {
            RoleDO newRole = new RoleDO()
                    .setRoleUuid(UuidUtil.generateStringUuid())
                    .setName(name)
                    .setDisplayName(displayName)
                    .setRoleDesc(desc);
            roleDAO.save(newRole);
            log.debug("[INIT] 创建角色 | [{}]{}", name, displayName);
        }
    }
}
