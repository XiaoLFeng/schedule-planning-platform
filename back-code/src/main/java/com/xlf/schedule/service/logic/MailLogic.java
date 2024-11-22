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

import com.xlf.schedule.constant.MailConstant;
import com.xlf.schedule.constant.SystemConstant;
import com.xlf.schedule.dao.MailCodeDAO;
import com.xlf.schedule.model.entity.MailCodeDO;
import com.xlf.schedule.service.MailService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.config.UtilConfiguration;
import com.xlf.utility.exception.BusinessException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件逻辑
 * <p>
 * 该类用于定义邮件逻辑;
 * 该类使用 {@link Service} 注解标记;
 * 该类实现 {@link MailService} 接口;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailLogic implements MailService {
    private final UtilConfiguration utilConfig;
    private final SpringTemplateEngine templateEngine;
    private final MailCodeDAO mailCodeDAO;

    @Override
    public void sendMail(String mail, String template, String subject, Map<String, String> parameters) {
        this.smtpSendMail(mail, template, subject, parameters);
    }

    @Override
    @Transactional
    public void sendMailCode(String mail, String code) {
        this.smtpSendMail(mail, "code", "验证码", Map.of("code", code));
        // 已有验证码删除
        mailCodeDAO.lambdaUpdate().eq(MailCodeDO::getMail, mail).remove();
        MailCodeDO mailCodeDO = new MailCodeDO()
                .setMail(mail)
                .setCode(code)
                .setExpiredAt(new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000));
        mailCodeDAO.save(mailCodeDO);
    }

    @Override
    @Transactional
    public MailCodeDO getMailCode(String mail) {
        MailCodeDO mailCodeDO = mailCodeDAO.lambdaQuery().eq(MailCodeDO::getMail, mail).one();
        if (mailCodeDO == null) {
            throw new BusinessException("验证码不存在", ErrorCode.NOT_EXIST);
        }
        if (mailCodeDO.getExpiredAt().before(new Timestamp(System.currentTimeMillis()))) {
            // 删除过期验证码
            mailCodeDAO.lambdaUpdate().eq(MailCodeDO::getCodeUuid, mailCodeDO.getCodeUuid()).remove();
            throw new BusinessException("验证码已过期", ErrorCode.NOT_EXIST);
        }
        return mailCodeDO;
    }

    @Override
    @Transactional
    public boolean verifyMailCode(String mail, String code) {
        MailCodeDO getMailCode = this.getMailCode(mail);
        if (getMailCode.getCode().equals(code)) {
            // 删除验证码
            mailCodeDAO.lambdaUpdate().eq(MailCodeDO::getCodeUuid, getMailCode.getCodeUuid()).remove();
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void deleteMailCode(String mail) {
        MailCodeDO getMailCode = this.getMailCode(mail);
        mailCodeDAO.lambdaUpdate().eq(MailCodeDO::getCodeUuid, getMailCode.getCodeUuid()).remove();
    }

    @Override
    @Transactional
    public boolean ableResendMailCode(String mail) {
        MailCodeDO getMailCode = this.getMailCode(mail);
        if (getMailCode == null) {
            return true;
        }
        if (getMailCode.getExpiredAt().before(new Timestamp(System.currentTimeMillis()))) {
            mailCodeDAO.lambdaUpdate().eq(MailCodeDO::getCodeUuid, getMailCode.getCodeUuid()).remove();
            return true;
        }
        // 验证码创建时间少于 60 秒，不允许重发
        return getMailCode.getCreatedAt().before(new Timestamp(System.currentTimeMillis() - 60 * 1000));
    }

    /**
     * 发送邮件
     *
     * @param mail       邮箱
     * @param template   模板
     * @param subject    主题
     * @param parameters 参数
     */
    private void smtpSendMail(String mail, String template, String subject, Map<String, String> parameters) {
        // 获取邮件模板
        ClassPathResource getMailTemplateResource = new ClassPathResource("templates/mail/" + template + ".html");
        if (!getMailTemplateResource.exists()) {
            throw new BusinessException("邮件模板不存在", ErrorCode.NOT_EXIST);
        }
        // 模板注入参数
        HashMap<String, Object> mailTemplateParameters = new HashMap<>(parameters);
        mailTemplateParameters.put("title", SystemConstant.SYSTEM_CHINESE_NAME + " - " + subject);
        mailTemplateParameters.put("mail", mail);
        mailTemplateParameters.put("copyright", SystemConstant.SYSTEM_CHINESE_COPYRIGHT);
        mailTemplateParameters.put("now_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        // 处理 Context
        Context context = new Context();
        context.setVariables(mailTemplateParameters);
        String getProcess = templateEngine
                .process(getMailTemplateResource.getPath().replace("templates/", ""), context);

        // 发送邮件
        MimeMessage mimeMessage = utilConfig.javaMailSender().createMimeMessage();
        try {
            MimeMessageHelper sendHelper = new MimeMessageHelper(mimeMessage);
            sendHelper.setFrom("<" + MailConstant.MAIL_USERNAME + ">" + MailConstant.MAIL_NICK_NAME);
            sendHelper.setTo(mail);
            sendHelper.setSubject(subject);
            sendHelper.setText(getProcess, true);
            utilConfig.javaMailSender().send(mimeMessage);
            log.debug("[MAIL] 发送邮件 {} 标题 {} 成功", mail, subject);
        } catch (MessagingException e) {
            throw new MailSendException("邮件发送失败：" + e.getMessage());
        }
    }
}
