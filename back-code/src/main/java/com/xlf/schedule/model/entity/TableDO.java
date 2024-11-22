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

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表实体
 * <p>
 * 该类用于定义表实体;
 * 该类使用 {@link Mapper} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Data
@TableName(value = "tables", schema = "information_schema")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TableDO {
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String selfReferencingColumnName;
    private String referenceGeneration;
    private String userDefinedTypeCatalog;
    private String userDefinedTypeSchema;
    private String userDefinedTypeName;
    private String isInsertableInto;
    private String isTyped;
    private String commitAction;
}
