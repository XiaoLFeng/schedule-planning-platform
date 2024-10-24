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

package com.xlf.schedule.exception.lib;

import com.xlf.utility.ErrorCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 非法数据异常
 * <p>
 * 该类用于定义非法数据异常;
 * 该类继承自 {@link RuntimeException} 类;
 * 该类使用 {@link Getter} 注解标记;
 * 该类使用 {@link ErrorCode}
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Getter
public class IllegalDataException extends RuntimeException {
    private final ErrorCode errorCode;

    /**
     * 构造函数
     * <p>
     * 该构造函数用于创建一个非法数据异常;
     * 该构造函数使用 {@link ErrorCode} 作为参数;
     *
     * @param errorCode 错误码
     */
    public IllegalDataException(@NotNull ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    public IllegalDataException(@NotNull ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
