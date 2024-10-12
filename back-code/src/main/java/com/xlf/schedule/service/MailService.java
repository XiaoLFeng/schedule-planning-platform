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

import com.xlf.schedule.model.entity.MailCodeDO;

import java.util.Map;

/**
 * 邮件服务接口
 * <br/>
 * 邮件服务，用于邮件相关操作。
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
public interface MailService {
    /**
     * 发送邮件
     * <p>
     * 该方法用于发送邮件；
     * 发送邮件时，需要提供 {@code 邮箱}、{@code 模板}、{@code 主题}、{@code 参数}；
     * 发送成功后，返回发送结果。
     *
     * @param mail 邮箱
     * @param template 模板
     * @param subject 主题
     * @param parameters 参数
     */
    void sendMail(String mail, String template, String subject, Map<String, String> parameters);

    /**
     * 发送邮件验证码
     * <p>
     * 该方法用于发送邮件验证码；
     * 发送邮件验证码时，需要提供 {@code 邮箱}、{@code 验证码}；
     * 发送成功后，返回发送结果。
     *
     * @param mail 邮箱
     * @param code 验证码
     */
    void sendMailCode(String mail, String code);

    /**
     * 获取邮件验证码
     * <p>
     * 该方法用于获取邮件验证码；
     * 获取邮件验证码时，需要提供 {@code 邮箱}；
     * 获取成功后，返回验证码。
     *
     * @param mail 邮箱
     * @return 验证码
     */
    MailCodeDO getMailCode(String mail);

    /**
     * 验证邮件验证码
     * <p>
     * 该方法用于验证邮件验证码；
     * 验证邮件验证码时，需要提供 {@code 邮箱}、{@code 验证码}；
     * 验证成功后，返回验证结果。
     *
     * @param mail 邮箱
     * @param code 验证码
     * @return 验证结果
     */
    boolean verifyMailCode(String mail, String code);

    /**
     * 删除邮件验证码
     * <p>
     * 该方法用于删除邮件验证码；
     * 删除邮件验证码时，需要提供 {@code 邮箱}；
     * 删除成功后，返回删除结果。
     *
     * @param mail 邮箱
     */
    void deleteMailCode(String mail);

    /**
     * 获取邮件验证码是否可重新发送
     * <p>
     * 该方法用于获取邮件验证码是否可重新发送；
     * 获取邮件验证码是否可重新发送时，需要提供 {@code 邮箱}；
     * 获取成功后，返回是否可重新发送。
     *
     * @param mail 邮箱
     * @return 是否可重新发送
     */
    boolean ableResendMailCode(String mail);
}
