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

package com.xlf.schedule.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 列表课程数据传输对象
 * <p>
 * 该类是列表课程数据传输对象类，用于定义列表课程数据传输对象的属性
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListCurriculumDTO {
    private String classGradeUuid;
    private String classTimeUuid;
    private Date semesterBegin;
    private Date semesterEnd;
    private String nickname;
}
