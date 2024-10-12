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

package com.xlf.schedule.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.schedule.mapper.LogsMapper;
import com.xlf.schedule.model.entity.LogsDO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

/**
 * 日志数据表数据访问对象
 * <p>
 * 该类用于定义日志数据表数据访问对象;
 * 该类使用 {@link Repository} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Repository
public class LogsDAO extends ServiceImpl<LogsMapper, LogsDO> implements IService<LogsDO> {

    /**
     * 保存日志
     *
     * @param type    日志类型
     * @param business 业务
     * @param user     用户
     * @param content  内容
     */
    public void save(@NotNull Integer type, @NotNull String business, String user, @NotNull String content) {
        LogsDO newLogsDO = new LogsDO()
                .setType(Short.valueOf(type.toString()))
                .setBusiness(business)
                .setUser(user)
                .setValue(content);
        this.save(newLogsDO);
    }
}