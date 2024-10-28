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

package com.xlf.schedule.service.logic;

import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.schedule.service.FileService;
import com.xlf.schedule.util.ProcessUtil;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * 文件服务逻辑
 * <p>
 * 该类是文件服务逻辑类，用于实现文件相关的服务方法
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Slf4j
@Service
public class FileLogic implements FileService {
    @Override
    public String uploadImage(String imageBase64) {
        // 获取图片的 base64 编码，并且识别图片的文件格式
        String suffix = ProcessUtil.getFileTypeWithBase64(imageBase64);
        if (suffix == null) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "文件格式不支持");
        }

        String imageName = UuidUtil.generateUuidNoDash() + "." + suffix;
        File newPreviewImage = new File("uploads/images/" + imageName);

        try {
            byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(bais);
            ImageIO.write(bufferedImage, suffix, newPreviewImage);
        } catch (IOException e) {
            throw new IllegalDataException(ErrorCode.OPERATION_FAILED, "文件上传失败");
        }
        return imageName;
    }

    @Override
    public void deleteImage(String imageUrl) {
        File file = new File("uploads/images/" + imageUrl);
        if (file.exists()) {
            if (file.delete()) {
                log.info("[FILE] 文件 {} 删除成功", imageUrl);
            } else {
                log.warn("[FILE] 文件 {} 删除失败", imageUrl);
            }
        }
    }
}
