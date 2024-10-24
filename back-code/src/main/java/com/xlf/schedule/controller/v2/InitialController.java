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

package com.xlf.schedule.controller.v2;

import com.xlf.schedule.constant.SystemConstant;
import com.xlf.schedule.model.vo.InitialSetupVO;
import com.xlf.schedule.service.InitialService;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 初始化控制器
 * <p>
 * 该类用于初始化控制器，主要用于初始化项目使用。
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/v2/initial")
@RequiredArgsConstructor
public class InitialController {
    private final InitialService initialService;

    /**
     * 初始化设置
     * <p>
     * 该方法用于初始化设置，主要用于初始化项目使用；当项目启动时，调用该接口进行初始化。
     *
     * @return {@link Void} 无返回值
     */
    @PostMapping("/set-up")
    public ResponseEntity<BaseResponse<Void>> setUp(
            @Validated @RequestBody InitialSetupVO initialSetupVO
    ) {
        if ("true".equals(SystemConstant.isInitialMode)) {
            initialService.setUp(initialSetupVO);
            return ResultUtil.success("初始化设置成功");
        } else {
            throw new BusinessException("非初始化模式", ErrorCode.OPERATION_DENIED);
        }
    }

    /**
     * 是否已初始化
     * <p>
     * 该方法用于判断是否已经初始化。
     *
     * @return {@link Boolean} 是否已初始化
     */
    @GetMapping("/is-initiated")
    public ResponseEntity<BaseResponse<Boolean>> isInitiated() {
        return ResultUtil.success("信息获取成功", "true".equals(SystemConstant.isInitialMode));
    }
}
