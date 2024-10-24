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

package com.xlf.schedule.exception;

import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.exception.PublicExceptionHandlerAbstract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 公共异常处理器
 * <p>
 * 该类用于处理公共异常;
 * 该类使用 {@link ControllerAdvice} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@ControllerAdvice
public class PublicExceptionHandler extends PublicExceptionHandlerAbstract {

    /**
     * 处理非法数据异常
     * <p>
     * 该方法用于处理非法数据异常;
     * 该方法使用 {@link ExceptionHandler} 注解标记;
     *
     * @param e 非法数据异常
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(IllegalDataException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalDataException(@NotNull IllegalDataException e) {
        return ResultUtil.error(e.getErrorCode(), e.getMessage(), null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpMessageNotReadableException(@NotNull HttpMessageNotReadableException ignored) {
        return ResultUtil.error(ErrorCode.BODY_ERROR, "消息不可读", null);
    }
}
