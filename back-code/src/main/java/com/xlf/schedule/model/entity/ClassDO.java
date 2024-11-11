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

package com.xlf.schedule.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 课程表实体
 * <p>
 * 该类用于定义课程表实体;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Data
@TableName("xf_class")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClassDO {

    /**
     * 课程表主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String classUuid;

    /**
     * 课程学年主键
     */
    private String classGradeUuid;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程星期编号
     */
    private Short dayTick;

    /**
     * 课程开始的节次编号
     */
    private Short startTick;

    /**
     * 课程结束的节次编号
     */
    private Short endTick;

    /**
     * 课程周次信息
     */
    private Short week;

    /**
     * 授课老师的名字
     */
    private String teacher;

    /**
     * 上课地点
     */
    private String location;

    /**
     * 记录创建时间
     */
    private Timestamp createdAt;

    /**
     * 记录更新时间
     */
    private Timestamp updatedAt;
}
