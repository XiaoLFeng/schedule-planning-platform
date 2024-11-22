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
import com.xlf.schedule.model.dto.SystemInfoDTO;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共控制器
 * <p>
 * 该类用于定义公共控制器;
 * 该类使用 {@link RestController} 注解标记;
 * 该类使用 {@link RequestMapping} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/v2/public")
public class PublicController {

    /**
     * 获取系统信息
     * <p>
     * 该系统用于获取系统信息，主要为本软件的基本信息；并非运行时的系统信息。
     *
     * @return {@link SystemInfoDTO} 系统信息
     */
    @GetMapping("/system/info")
    public ResponseEntity<BaseResponse<SystemInfoDTO>> getSystemInfo() {
        SystemInfoDTO systemInfoDTO = new SystemInfoDTO();
        systemInfoDTO
                .setSystemName(SystemConstant.getSYSTEM_NAME())
                .setSystemChineseName(SystemConstant.getSYSTEM_CHINESE_NAME())
                .setSystemVersion(SystemConstant.getSYSTEM_VERSION())
                .setSystemAuthor(SystemConstant.getSYSTEM_AUTHOR())
                .setSystemAuthorEmail(SystemConstant.getSYSTEM_AUTHOR_EMAIL())
                .setSystemAuthorUrl(SystemConstant.getSYSTEM_AUTHOR_URL())
                .setSystemLicense(SystemConstant.getSYSTEM_LICENSE())
                .setSystemLicenseUrl(SystemConstant.getSYSTEM_LICENSE_URL())
                .setSystemDisclaimer(SystemConstant.getSYSTEM_DISCLAIMER())
                .setSystemChineseDisclaimer(SystemConstant.getSYSTEM_CHINESE_DISCLAIMER())
                .setSystemAbout(SystemConstant.getSYSTEM_ABOUT())
                .setSystemChineseAbout(SystemConstant.getSYSTEM_CHINESE_ABOUT())
                .setSystemLicenseStatement(SystemConstant.getSYSTEM_LICENSE_STATEMENT())
                .setSystemChineseLicenseStatement(SystemConstant.getSYSTEM_CHINESE_LICENSE_STATEMENT())
                .setSystemCopyright(SystemConstant.getSYSTEM_COPYRIGHT());
        return ResultUtil.success("成功", systemInfoDTO);
    }
}
