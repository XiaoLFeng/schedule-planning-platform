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

package com.xlf.schedule.service;

import com.xlf.schedule.model.vo.InitialSetupVO;
import org.jetbrains.annotations.NotNull;

/**
 * 初始化服务
 * <p>
 * 该类用于定义初始化服务;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
public interface InitialService {
    /**
     * 设置初始化
     * <p>
     * 该方法用于设置初始化，初始化设置值对象；主要为创建超级管理员用户以及创建测试用户。
     *
     * @param initialSetupVO 初始化设置值对象
     */
    void setUp(@NotNull InitialSetupVO initialSetupVO);
}
