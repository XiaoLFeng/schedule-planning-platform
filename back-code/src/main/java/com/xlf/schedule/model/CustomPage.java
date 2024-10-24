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

package com.xlf.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 分页
 * <p>
 * 该类用于定义分页；
 * 该类包含以下字段：
 * <ul>
 *     <li>{@link List<T>} records</li>
 *     <li>{@link Long} total</li>
 *     <li>{@link Long} size</li>
 *     <li>{@link Long} current</li>
 *     <li>{@link Long} pages</li>
 * </ul>
 * 该类用于分页查询时返回分页数据。
 *
 * @param <T> 分页数据类型
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomPage<T> {
    private List<T> records;
    private Long total;
    private Long size;
    private Long current;
    private Long pages;
}
