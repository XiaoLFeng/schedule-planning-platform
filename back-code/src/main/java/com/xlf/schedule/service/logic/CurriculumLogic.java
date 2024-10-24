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

import com.xlf.schedule.dao.ClassGradeDAO;
import com.xlf.schedule.model.dto.ClassGradeDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.entity.ClassGradeDO;
import com.xlf.schedule.service.CurriculumService;
import com.xlf.schedule.service.RoleService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import com.xlf.utility.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * 课程表逻辑
 * <p>
 * 该类用于定义课程表逻辑;
 * 该类使用 {@link Service} 注解标记;
 * 该类实现 {@link CurriculumService} 接口;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurriculumLogic implements CurriculumService {

    private final ClassGradeDAO classGradeDAO;
    private final RoleService roleService;

    @Override
    public String createClassGrade(String name, Date begin, Date end, String userUuid) {
        boolean exists = classGradeDAO.lambdaQuery()
                .eq(ClassGradeDO::getNickname, name)
                .eq(ClassGradeDO::getSemesterBegin, begin)
                .exists();
        if (exists) {
            throw new BusinessException("课程表已存在", ErrorCode.EXISTED);
        }
        // 检查开始时间是否为周一
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            throw new BusinessException("开始时间必须为周一", ErrorCode.BODY_INVALID);
        }
        ClassGradeDO classGradeDO = new ClassGradeDO();
        String classGradeUuid = UuidUtil.generateUuidNoDash();
        classGradeDO
                .setClassGradeUuid(classGradeUuid)
                .setNickname(name)
                .setSemesterBegin(new java.sql.Date(begin.getTime()))
                .setUserUuid(userUuid);
        if (end != null) {
            classGradeDO.setSemesterEnd(new java.sql.Date(end.getTime()));
        }
        classGradeDAO.save(classGradeDO);
        return classGradeUuid;
    }

    @Override
    public ClassGradeDTO getClassGrade(String uuid) {
        ClassGradeDO classGradeDO = classGradeDAO.lambdaQuery()
                .eq(ClassGradeDO::getClassGradeUuid, uuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("课程表不存在", ErrorCode.NOT_EXIST));
        ClassGradeDTO classGradeDTO = new ClassGradeDTO();
        BeanUtils.copyProperties(classGradeDO, classGradeDTO);
        return classGradeDTO;
    }

    @Override
    public void deleteClassGrade(@NotNull UserDTO userDTO, String classGradeUuid) {
        ClassGradeDO getClassGrade = classGradeDAO.lambdaQuery()
                .eq(ClassGradeDO::getUserUuid, classGradeUuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("课程表不存在", ErrorCode.NOT_EXIST));
        if (!getClassGrade.getUserUuid().equals(userDTO.getUuid())) {
            if (!roleService.checkRoleHasAdmin(userDTO.getRole())) {
                throw new BusinessException("您没有权限删除", ErrorCode. OPERATION_DENIED);
            }
        }
        classGradeDAO.lambdaUpdate().eq(ClassGradeDO::getUserUuid, userDTO).remove();
    }

    @Override
    public void editClassGrade(String uuid, String name, Date begin, Date end, @NotNull UserDTO userDTO) {
        ClassGradeDO classGradeDO = classGradeDAO.lambdaQuery()
                .eq(ClassGradeDO::getUserUuid, uuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("课程表不存在", ErrorCode.NOT_EXIST));
        if (!classGradeDO.getUserUuid().equals(userDTO.getUuid())) {
            if (!roleService.checkRoleHasAdmin(userDTO.getRole())) {
                throw new BusinessException("您没有权限编辑", ErrorCode. OPERATION_DENIED);
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            throw new BusinessException("开始时间必须为周一", ErrorCode.BODY_INVALID);
        }
        classGradeDO
                .setNickname(name)
                .setSemesterBegin(new java.sql.Date(begin.getTime()))
                .setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if (end != null) {
            classGradeDO.setSemesterEnd(new java.sql.Date(end.getTime()));
        }
        classGradeDAO.updateById(classGradeDO);
    }
}
