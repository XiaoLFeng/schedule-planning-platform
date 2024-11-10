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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xlf.schedule.constant.SystemConstant;
import com.xlf.schedule.dao.ClassDAO;
import com.xlf.schedule.dao.ClassGradeDAO;
import com.xlf.schedule.dao.ClassTimeMarketDAO;
import com.xlf.schedule.dao.ClassTimeMyDAO;
import com.xlf.schedule.model.dto.ClassDTO;
import com.xlf.schedule.model.dto.ClassGradeDTO;
import com.xlf.schedule.model.dto.ClassTimeDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.dto.json.ClassTimeAbleDTO;
import com.xlf.schedule.model.entity.ClassDO;
import com.xlf.schedule.model.entity.ClassGradeDO;
import com.xlf.schedule.model.entity.ClassTimeMarketDO;
import com.xlf.schedule.model.entity.ClassTimeMyDO;
import com.xlf.schedule.model.vo.ClassTimeVO;
import com.xlf.schedule.model.vo.ClassVO;
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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    private final Gson gson;
    private final ClassTimeMarketDAO classTimeMarketDAO;
    private final ClassTimeMyDAO classTimeMyDAO;
    private final ClassDAO classDAO;

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
    public ClassGradeDTO getClassGrade(@NotNull UserDTO userDTO, String uuid) {
        ClassGradeDO getClassGrade = classGradeDAO.lambdaQuery()
                .eq(ClassGradeDO::getClassGradeUuid, uuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("课程表不存在", ErrorCode.NOT_EXIST));
        if (!getClassGrade.getUserUuid().equals(userDTO.getUuid())) {
            if (!roleService.checkRoleHasAdmin(userDTO.getRole())) {
                throw new BusinessException("您没有权限查看", ErrorCode.OPERATION_DENIED);
            }
        }
        List<ClassDTO> classList = new ArrayList<>();
        classDAO.lambdaQuery()
                .eq(ClassDO::getClassGradeUuid, uuid)
                .list().forEach(classDO -> {
                    if (classList.stream().noneMatch(classDTO -> classDTO.getName().equals(classDO.getName()))) {
                        ClassDTO classDTO = new ClassDTO();
                        List<Short> weeks = new ArrayList<>();
                        BeanUtils.copyProperties(classDO, classDTO);
                        classDTO.setWeek(weeks);
                        classList.add(classDTO);
                    } else {
                        classList.stream()
                                .filter(classDTO -> classDTO.getName().equals(classDO.getName()))
                                .findFirst()
                                .ifPresent(classDTO -> classDTO.getWeek().add(classDO.getWeek()));
                    }
                });
        ClassGradeDTO classGradeDTO = new ClassGradeDTO();
        BeanUtils.copyProperties(getClassGrade, classGradeDTO);
        classGradeDTO.setClassList(classList);
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
                throw new BusinessException("您没有权限删除", ErrorCode.OPERATION_DENIED);
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
                throw new BusinessException("您没有权限编辑", ErrorCode.OPERATION_DENIED);
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

    @Override
    @Transactional
    public void createClassTime(@NotNull UserDTO userDTO, @NotNull ClassTimeVO classTimeVO) {
        checkTimeAbleUseful(classTimeVO);
        String timeAble = gson.toJson(classTimeVO.getTimeAble());
        String classTimeUuid = UuidUtil.generateUuidNoDash();
        ClassTimeMarketDO classTimeMarketDO = new ClassTimeMarketDO();
        classTimeMarketDO
                .setClassTimeMarketUuid(classTimeUuid)
                .setUserUuid(userDTO.getUuid())
                .setName(classTimeVO.getName())
                .setTimetable(timeAble)
                .setIsPublic(classTimeVO.getIsPublic());
        ClassTimeMyDO classTimeMyDO = new ClassTimeMyDO();
        classTimeMyDO
                .setClassTimeMyUuid(UuidUtil.generateUuidNoDash())
                .setTimeMarketUuid(classTimeUuid)
                .setUserUuid(userDTO.getUuid());
        classTimeMarketDAO.save(classTimeMarketDO);
        classTimeMyDAO.save(classTimeMyDO);
    }

    @Override
    public void editClassTime(@NotNull UserDTO userDTO, String classTimeUuid, ClassTimeVO classTimeVO) {
        ClassTimeMarketDO classTimeMarketDO = classTimeMarketDAO.lambdaQuery()
                .eq(ClassTimeMarketDO::getClassTimeMarketUuid, classTimeUuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("课程时间不存在", ErrorCode.NOT_EXIST));
        if (classTimeMarketDO.getIsOfficial()) {
            throw new BusinessException("录入官方课程时间不允许修改", ErrorCode.OPERATION_DENIED);
        }
        if (!classTimeMarketDO.getUserUuid().equals(userDTO.getUuid())) {
            if (!roleService.checkRoleHasAdmin(userDTO.getRole())) {
                throw new BusinessException("您没有权限编辑", ErrorCode.OPERATION_DENIED);
            }
        }
        checkTimeAbleUseful(classTimeVO);
        String timeAble = gson.toJson(classTimeVO.getTimeAble());
        classTimeMarketDO
                .setName(classTimeVO.getName())
                .setTimetable(timeAble)
                .setIsPublic(classTimeVO.getIsPublic())
                .setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        classTimeMarketDAO.updateById(classTimeMarketDO);
    }

    @Override
    @Transactional
    public void deleteClassTime(UserDTO userDTO, String classTimeUuid) {
        ClassTimeMarketDO classTimeMarketDO = classTimeMarketDAO.lambdaQuery()
                .eq(ClassTimeMarketDO::getClassTimeMarketUuid, classTimeUuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("课程时间不存在", ErrorCode.NOT_EXIST));
        if (classTimeMarketDO.getIsOfficial()) {
            throw new BusinessException("录入官方课程时间不允许删除", ErrorCode.OPERATION_DENIED);
        }
        if (classTimeMarketDO.getClassTimeMarketUuid().equals(SystemConstant.defaultClassTimeUUID)) {
            throw new BusinessException("默认课程时间不允许删除", ErrorCode.OPERATION_DENIED);
        }
        if (!classTimeMarketDO.getUserUuid().equals(userDTO.getUuid())) {
            if (!roleService.checkRoleHasAdmin(userDTO.getRole())) {
                throw new BusinessException("您没有权限删除", ErrorCode.OPERATION_DENIED);
            }
        }
        // TODO[241025001] - 添加邮件发送通知
        // 配置原有课表时间转为默认时间
        classGradeDAO.lambdaUpdate().eq(ClassGradeDO::getClassTimeUuid, classTimeUuid)
                .set(ClassGradeDO::getClassTimeUuid, SystemConstant.defaultClassTimeUUID)
                .update();
        classTimeMyDAO.lambdaUpdate().eq(ClassTimeMyDO::getTimeMarketUuid, classTimeUuid).remove();
        classTimeMarketDAO.lambdaUpdate().eq(ClassTimeMarketDO::getClassTimeMarketUuid, classTimeUuid).remove();
    }

    @Override
    public Page<ClassTimeMarketDO> getClassTimeMarketList(Integer page, Integer size) {
        return classTimeMarketDAO.lambdaQuery().page(new Page<>(page, size));
    }

    @Override
    public ClassTimeDTO getClassTimeMarket(String classTimeMarketUuid) {
        ClassTimeMarketDO classTimeMarketDO = classTimeMarketDAO.lambdaQuery()
                .eq(ClassTimeMarketDO::getClassTimeMarketUuid, classTimeMarketUuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("课程时间不存在", ErrorCode.NOT_EXIST));
        ClassTimeDTO classTimeDTO = new ClassTimeDTO();
        BeanUtils.copyProperties(classTimeMarketDO, classTimeDTO);
        List<ClassTimeAbleDTO> timeAble = gson.fromJson(classTimeMarketDO.getTimetable(), new TypeToken<>() {
        }.getType());
        classTimeDTO.setTimetable(timeAble);
        return classTimeDTO;
    }

    @Override
    public void addMyClassTime(@NotNull UserDTO userDTO, String classTimeMarketUuid) {
        classTimeMarketDAO.lambdaQuery()
                .eq(ClassTimeMarketDO::getClassTimeMarketUuid, classTimeMarketUuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("课程时间不存在", ErrorCode.NOT_EXIST));
        classTimeMyDAO.lambdaQuery()
                .eq(ClassTimeMyDO::getUserUuid, userDTO.getUuid())
                .eq(ClassTimeMyDO::getTimeMarketUuid, classTimeMarketUuid)
                .oneOpt()
                .ifPresentOrElse(classTimeMyDO -> {
                    throw new BusinessException("您已添加该课程时间", ErrorCode.EXISTED);
                }, () -> {
                    ClassTimeMyDO classTimeMyDO = new ClassTimeMyDO();
                    classTimeMyDO
                            .setClassTimeMyUuid(UuidUtil.generateUuidNoDash())
                            .setTimeMarketUuid(classTimeMarketUuid)
                            .setUserUuid(userDTO.getUuid());
                    classTimeMyDAO.save(classTimeMyDO);
                });
    }

    @Override
    public void deleteMyClassTime(@NotNull UserDTO userDTO, String classTimeMarketUuid) {
        classTimeMyDAO.lambdaQuery()
                .eq(ClassTimeMyDO::getUserUuid, userDTO.getUuid())
                .eq(ClassTimeMyDO::getTimeMarketUuid, classTimeMarketUuid)
                .oneOpt()
                .ifPresentOrElse(classTimeMyDAO::removeById, () -> {
                    throw new BusinessException("您未添加该课程时间", ErrorCode.NOT_EXIST);
                });
    }

    @Override
    public Page<ClassTimeMarketDO> getMyClassTimeList(@NotNull UserDTO userDTO, Integer page, Integer size) {
        List<String> stringList = classTimeMyDAO.lambdaQuery()
                .eq(ClassTimeMyDO::getUserUuid, userDTO.getUuid())
                .list().stream().map(ClassTimeMyDO::getTimeMarketUuid).toList();
        if (stringList.isEmpty()) {
            return classTimeMarketDAO.lambdaQuery()
                    .eq(ClassTimeMarketDO::getClassTimeMarketUuid, SystemConstant.defaultClassTimeUUID)
                    .page(new Page<>(page, size));
        } else {
            return classTimeMarketDAO.lambdaQuery()
                    .in(ClassTimeMarketDO::getClassTimeMarketUuid, stringList)
                    .or()
                    .eq(ClassTimeMarketDO::getClassTimeMarketUuid, SystemConstant.defaultClassTimeUUID)
                    .page(new Page<>(page, size));
        }
    }

    @Override
    public ClassTimeDTO getMyClassTime(UserDTO userDTO, String classTimeMarketUuid) {
        ClassTimeDTO classTimeDTO = new ClassTimeDTO();
        classTimeMarketDAO.lambdaQuery()
                .eq(ClassTimeMarketDO::getClassTimeMarketUuid, classTimeMarketUuid)
                .oneOpt()
                .ifPresentOrElse(classTimeMarketDO -> {
                    if (!classTimeMarketDO.getClassTimeMarketUuid().equals(SystemConstant.defaultClassTimeUUID)) {
                        classTimeMyDAO.lambdaQuery()
                                .eq(ClassTimeMyDO::getUserUuid, userDTO.getUuid())
                                .eq(ClassTimeMyDO::getTimeMarketUuid, classTimeMarketUuid)
                                .or()
                                .oneOpt()
                                .ifPresentOrElse(classTimeMyDO -> {
                                    BeanUtils.copyProperties(classTimeMarketDO, classTimeDTO);
                                    List<ClassTimeAbleDTO> timeAble = gson.fromJson(classTimeMarketDO.getTimetable(), new TypeToken<>() {
                                    }.getType());
                                    classTimeDTO.setTimetable(timeAble);
                                }, () -> {
                                    throw new BusinessException("您未添加该课程时间", ErrorCode.NOT_EXIST);
                                });
                    } else {
                        BeanUtils.copyProperties(classTimeMarketDO, classTimeDTO);
                        List<ClassTimeAbleDTO> timeAble = gson.fromJson(classTimeMarketDO.getTimetable(), new TypeToken<>() {
                        }.getType());
                        classTimeDTO.setTimetable(timeAble);
                    }
                }, () -> {
                    throw new BusinessException("课程时间不存在", ErrorCode.NOT_EXIST);
                });
        return classTimeDTO;
    }

    @Override
    @Transactional
    public void addClass(@NotNull UserDTO userDTO, @NotNull ClassVO classVO) {
        // 检查 classGradeUuid 是否存在
        ClassGradeDO classGradeDO = classGradeDAO.lambdaQuery()
                .eq(ClassGradeDO::getClassGradeUuid, classVO.getClassGradeUuid())
                .oneOpt()
                .orElseThrow(() -> new BusinessException("课程表不存在", ErrorCode.NOT_EXIST));
        if (!classGradeDO.getUserUuid().equals(userDTO.getUuid())) {
            if (!roleService.checkRoleHasAdmin(userDTO.getRole())) {
                throw new BusinessException("您没有权限添加", ErrorCode.OPERATION_DENIED);
            }
        }
        // 对 Week 进行遍历循环
        classVO.getWeeks().forEach(week -> {
            ClassDO newClass = new ClassDO();
            newClass
                    .setClassGradeUuid(classGradeDO.getClassGradeUuid())
                    .setName(classVO.getName())
                    .setStartTick(classVO.getStartTick())
                    .setEndTick(classVO.getEndTick())
                    .setWeek(week)
                    .setTeacher(classVO.getTeacher())
                    .setLocation(classVO.getLocation());
            classDAO.save(newClass);
        });
    }

    @Override
    public void moveClass(UserDTO userDTO, String classUuid, Short week, Short startTick, Short endTick) {
        classDAO.lambdaQuery()
                .eq(ClassDO::getClassUuid, classUuid)
                .oneOpt()
                .ifPresentOrElse(classDO -> {
                    ClassGradeDO classGradeDO = classGradeDAO.lambdaQuery()
                            .eq(ClassGradeDO::getClassGradeUuid, classDO.getClassGradeUuid())
                            .oneOpt()
                            .orElseThrow(() -> new BusinessException("课程表不存在", ErrorCode.NOT_EXIST));
                    if (!classGradeDO.getUserUuid().equals(userDTO.getUuid())) {
                        if (!roleService.checkRoleHasAdmin(userDTO.getRole())) {
                            throw new BusinessException("您没有权限编辑", ErrorCode.OPERATION_DENIED);
                        }
                    }
                    classDO
                            .setWeek(week)
                            .setStartTick(startTick)
                            .setEndTick(endTick);
                    classDAO.updateById(classDO);
                }, () -> {
                    throw new BusinessException("课程不存在", ErrorCode.NOT_EXIST);
                });
    }

    @Override
    public void deleteClass(UserDTO userDTO, String classUuid) {
        classDAO.lambdaQuery()
                .eq(ClassDO::getClassUuid, classUuid)
                .oneOpt()
                .ifPresentOrElse(classDO -> {
                    ClassGradeDO classGradeDO = classGradeDAO.lambdaQuery()
                            .eq(ClassGradeDO::getClassGradeUuid, classDO.getClassGradeUuid())
                            .oneOpt()
                            .orElseThrow(() -> new BusinessException("课程表不存在", ErrorCode.NOT_EXIST));
                    if (!classGradeDO.getUserUuid().equals(userDTO.getUuid())) {
                        if (!roleService.checkRoleHasAdmin(userDTO.getRole())) {
                            throw new BusinessException("您没有权限删除", ErrorCode.OPERATION_DENIED);
                        }
                    }
                    classDAO.removeById(classUuid);
                }, () -> {
                    throw new BusinessException("课程不存在", ErrorCode.NOT_EXIST);
                });
    }

    /**
     * 检查时间是否有效
     *
     * @param classTimeVO 课程时间值对象
     */
    private void checkTimeAbleUseful(@NotNull ClassTimeVO classTimeVO) {
        // 时间格式为 24 小时，格式为 00:00，检查列表时间是否是顺序
        AtomicReference<String> lastEndTime = new AtomicReference<>("00:00");
        classTimeVO.getTimeAble().forEach(timeAble -> {
            if (timeAble.getStartTime().compareTo(timeAble.getEndTime()) >= 0) {
                throw new BusinessException("课程时间开始时间不能大于等于结束时间", ErrorCode.BODY_INVALID);
            }
            if (timeAble.getStartTime().compareTo(lastEndTime.get()) < 0) {
                throw new BusinessException("课程时间列表时间不是顺序", ErrorCode.BODY_INVALID);
            }
            lastEndTime.set(timeAble.getEndTime());
        });
    }
}
