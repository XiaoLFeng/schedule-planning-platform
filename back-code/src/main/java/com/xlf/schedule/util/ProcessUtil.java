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

package com.xlf.schedule.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 处理工具类
 * <p>
 * 该类是处理工具类，用于处理一些通用的处理方法
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
public class ProcessUtil {
    private ProcessUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取文件类型
     * <p>
     *     获取文件类型
     * </p>
     *
     * @param base64File 文件的 Base64 编码
     * @return 文件类型
     */
    @Nullable
    public static String getFileTypeWithBase64(@NotNull String base64File) {
        // 判断文件类型
        if (base64File.startsWith("data:image/jpeg;base64")) {
            return "jpg";
        } else if (base64File.startsWith("data:image/png;base64")) {
            return "png";
        } else if (base64File.startsWith("data:image/gif;base64")) {
            return "gif";
        } else if (base64File.startsWith("data:image/bmp;base64")) {
            return "bmp";
        } else {
            return null;
        }
    }
}
