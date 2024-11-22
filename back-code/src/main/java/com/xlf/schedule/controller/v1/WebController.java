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

package com.xlf.schedule.controller.v1;

import com.xlf.schedule.constant.WebConstant;
import com.xlf.schedule.model.dto.WebInfoDTO;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网页控制器
 * <p>
 * 该类用于定义Web控制器;
 * 该类使用 {@link RestController} 注解标记;
 * 该类使用 {@link RequestMapping} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/v1/web")
@RequiredArgsConstructor
public class WebController {

    /**
     * 获取网页信息
     * <p>
     * 该方法用于获取数据库信息，返回给前端；
     * 该数据用于前端的基础页面响应显示。
     *
     * @return {@link WebInfoDTO} 网页信息
     */
    @GetMapping("/info")
    public ResponseEntity<BaseResponse<WebInfoDTO>> info() {
        WebInfoDTO newInfo = new WebInfoDTO();
        newInfo
                .setAuthor(WebConstant.getAuthor())
                .setVersion(WebConstant.getVersion())
                .setLicense(WebConstant.getLicense())
                .setDescription(WebConstant.getDescription())
                .setIcp(WebConstant.getIcp())
                .setName(WebConstant.getName())
                .setKeywords(WebConstant.getKeywords())
                .setData(WebConstant.getGongan())
                .setCopyright(WebConstant.getCopyright());
        return ResultUtil.success("测试输出", newInfo);
    }
}
