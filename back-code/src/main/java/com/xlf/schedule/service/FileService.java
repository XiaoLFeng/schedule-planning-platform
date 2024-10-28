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

/**
 * 文件服务接口
 * <p>
 * 该接口是文件服务接口，用于定义文件相关的服务方法
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
public interface FileService {

    /**
     * 上传图片
     * <p>
     * 该方法用于上传图片
     *
     * @param imageBase64 图片base64字符串
     * @return 图片url
     */
    String uploadImage(String imageBase64);

    /**
     * 删除图片
     * <p>
     * 该方法用于删除图片
     *
     * @param imageUrl 图片url
     */
    void deleteImage(String imageUrl);
}
