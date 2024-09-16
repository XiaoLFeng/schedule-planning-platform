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

import com.xlf.schedule.model.entity.MailCodeDO;
import com.xlf.schedule.model.vo.MailSendVO;
import com.xlf.schedule.service.MailService;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.exception.BusinessException;
import com.xlf.utility.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件控制器
 * <p>
 * 该类用于定义邮件控制器;
 * 该类使用 {@link RestController} 注解标记;
 * 该类使用 {@link RequestMapping} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    /**
     * 发送邮件
     * <p>
     * 该方法用于发送邮件；
     * 发送邮件时，需要提供 {@code 邮箱}、{@code 模板}、{@code 主题}、{@code 参数}；
     * 发送成功后，返回发送结果。
     *
     * @param mailSendVO 邮件发送视图对象
     */
    @PostMapping("/send")
    public ResponseEntity<BaseResponse<Void>> sendMail(
            @Validated @RequestBody MailSendVO mailSendVO
    ) {
        String random = RandomUtil.createRandomString(6);
        try {
            this.sendMail(mailSendVO, random);
        } catch (BusinessException e) {
            if (e.getErrorCode().equals(ErrorCode.NOT_EXIST)) {
                mailService.sendMailCode(mailSendVO.getMail(), random);
            } else {
                this.sendMail(mailSendVO, random);
            }
        }
        return ResultUtil.success("发送成功");
    }

    /**
     * 发送邮件
     * <p>
     * 该方法用于发送邮件；
     * 发送邮件时，需要提供 {@code 邮箱}、{@code 随机码}；
     * 发送成功后，返回发送结果。
     *
     * @param mailSendVO 邮件发送视图对象
     * @param random     随机码
     */
    private void sendMail(@NotNull MailSendVO mailSendVO, String random) {
        MailCodeDO getMailCode = mailService.getMailCode(mailSendVO.getMail());
        if (getMailCode == null) {
            mailService.sendMailCode(mailSendVO.getMail(), random);
        } else {
            if (mailService.ableResendMailCode(mailSendVO.getMail())) {
                mailService.sendMailCode(mailSendVO.getMail(), random);
            } else {
                throw new BusinessException("邮件发送过于频繁，请稍后再试", ErrorCode.OPERATION_DENIED);
            }
        }
    }
}
