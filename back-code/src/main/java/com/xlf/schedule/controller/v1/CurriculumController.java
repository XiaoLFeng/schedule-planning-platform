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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.schedule.model.CustomPage;
import com.xlf.schedule.model.dto.ClassGradeDTO;
import com.xlf.schedule.model.dto.ClassTimeDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.dto.json.ClassTimeAbleDTO;
import com.xlf.schedule.model.entity.ClassTimeMarketDO;
import com.xlf.schedule.model.vo.ClassGradeVO;
import com.xlf.schedule.model.vo.ClassTimeVO;
import com.xlf.schedule.model.vo.ClassVO;
import com.xlf.schedule.service.CurriculumService;
import com.xlf.schedule.service.UserService;
import com.xlf.schedule.util.CopyUtil;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.annotations.HasAuthorize;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 课程表控制器
 * <p>
 * 该类用于定义课程表控制器;
 * 该类使用 {@link RestController} 注解标记;
 * 该类使用 {@link RequestMapping} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/curriculum")
@RequiredArgsConstructor
public class CurriculumController {
    private final UserService userService;
    private final CurriculumService curriculumService;
    private final Gson gson;

    /**
     * 创建课程表
     * <p>
     * 用于创建一个新的年度学期，可以在该年度学期中创建课程信息。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link ClassGradeDTO}>>
     */
    @HasAuthorize
    @PostMapping("/grade")
    public ResponseEntity<BaseResponse<ClassGradeDTO>> createClassGrade(
            @RequestBody @Validated ClassGradeVO classGradeVO,
            @NotNull HttpServletRequest request
    ) {
        try {
            Date startTime = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(classGradeVO.getSemesterBegin()).getTime());
            Date endTime = null;
            if (classGradeVO.getSemesterEnd() != null && !classGradeVO.getSemesterEnd().isBlank()) {
                endTime = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(classGradeVO.getSemesterEnd()).getTime());
            }
            if (endTime != null && endTime.before(startTime)) {
                log.debug(endTime.toString());
                throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "结束时间不能早于开始时间");
            }
            UserDTO getUser = userService.getUserByToken(request);
            String classGradeUuid = curriculumService.createClassGrade(
                    classGradeVO.getGradeName(),
                    startTime,
                    endTime,
                    getUser.getUuid(),
                    classGradeVO.getClassTimeUuid()
            );
            ClassGradeDTO classGrade = curriculumService.getClassGrade(getUser, classGradeUuid);
            return ResultUtil.success("操作成功", classGrade);
        } catch (ParseException e) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "时间格式非法");
        }
    }

    /**
     * 删除课程表
     * <p>
     * 用于删除一个年度学期，删除后，该年度学期中的课程信息也会被删除。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @DeleteMapping("/grade/{class_grade_uuid}")
    public ResponseEntity<BaseResponse<Void>> deleteClassGrade(
            @PathVariable("class_grade_uuid") String classGradeUuid,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classGradeUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程表UUID非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.deleteClassGrade(getUser, classGradeUuid);
        return ResultUtil.success("操作成功");
    }

    /**
     * 编辑课程表
     * <p>
     * 用于编辑一个年度学期，可以修改年度学期的名称、开始时间、结束时间。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @PutMapping("/grade/{class_grade_uuid}")
    public ResponseEntity<BaseResponse<Void>> editClassGrade(
            @PathVariable("class_grade_uuid") String classGradeUuid,
            @RequestBody @Validated @NotNull ClassGradeVO classGradeVO,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classGradeUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程表UUID非法");
        }
        try {
            UserDTO getUser = userService.getUserByToken(request);
            if (classGradeVO.getSemesterEnd() != null) {
                curriculumService.editClassGrade(
                        classGradeUuid,
                        classGradeVO.getGradeName(),
                        new Date(new SimpleDateFormat("yyyy-MM-dd").parse(classGradeVO.getSemesterBegin()).getTime()),
                        new Date(new SimpleDateFormat("yyyy-MM-dd").parse(classGradeVO.getSemesterEnd()).getTime()),
                        getUser,
                        classGradeVO.getClassTimeUuid()
                );
            } else {
                curriculumService.editClassGrade(
                        classGradeUuid,
                        classGradeVO.getGradeName(),
                        new Date(new SimpleDateFormat("yyyy-MM-dd").parse(classGradeVO.getSemesterBegin()).getTime()),
                        null,
                        getUser,
                        classGradeVO.getClassTimeUuid()
                );
            }
            return ResultUtil.success("操作成功");
        } catch (ParseException e) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL);
        }
    }

    /**
     * 获取课程表
     * <p>
     * 用于获取一个年度学期的详细信息。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link ClassGradeDTO}>>
     */
    @HasAuthorize
    @GetMapping("/grade/{class_grade_uuid}")
    public ResponseEntity<BaseResponse<ClassGradeDTO>> getClassGrade(
            @PathVariable("class_grade_uuid") String classGradeUuid,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classGradeUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程表UUID非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        ClassGradeDTO classGrade = curriculumService.getClassGrade(getUser, classGradeUuid);
        return ResultUtil.success("操作成功", classGrade);
    }

    /**
     * 创建课程时间
     * <p>
     * 用于创建一个课程时间，可以在该课程时间中创建课程信息。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @Transactional
    @PostMapping("/time")
    public ResponseEntity<BaseResponse<Void>> createClassTime(
            @RequestBody @Validated ClassTimeVO classTimeVO,
            @NotNull HttpServletRequest request
    ) {
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.createClassTime(getUser, classTimeVO);
        return ResultUtil.success("操作成功");
    }

    /**
     * 修改课程时间
     * <p>
     * 用于修改一个课程时间，可以修改课程时间的名称、是否公开、时间列表。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @Transactional
    @PutMapping("/time/{class_time_uuid}")
    public ResponseEntity<BaseResponse<Void>> editClassTime(
            @PathVariable("class_time_uuid") String classTimeUuid,
            @RequestBody @Validated ClassTimeVO classTimeVO,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classTimeUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程时间UUID非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.editClassTime(getUser, classTimeUuid, classTimeVO);
        return ResultUtil.success("操作成功");
    }

    /**
     * 删除课程时间
     * <p>
     * 用于删除一个课程时间，删除后，该课程时间中的课程信息也会被删除。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @Transactional
    @DeleteMapping("/time/{class_time_uuid}")
    public ResponseEntity<BaseResponse<Void>> deleteClassTime(
            @PathVariable("class_time_uuid") String classTimeUuid,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classTimeUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程时间UUID非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.deleteClassTime(getUser, classTimeUuid);
        return ResultUtil.success("操作成功");
    }

    /**
     * 获取课程时间市场列表
     * <p>
     * 用于获取一个课程时间的市场列表。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link ClassTimeDTO}>>
     */
    @HasAuthorize
    @GetMapping("/time")
    public ResponseEntity<BaseResponse<CustomPage<ClassTimeDTO>>> getClassTimeMarketList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size
    ) {
        Page<ClassTimeMarketDO> classTimeMarketList = curriculumService.getClassTimeMarketList(page, size);
        return this.classTimeMarkCustomPage(classTimeMarketList);
    }

    /**
     * 获取课程时间市场
     * <p>
     * 用于获取一个课程时间的市场信息。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link ClassTimeDTO}>>
     */
    @HasAuthorize
    @GetMapping("/time/{class_time_market_uuid}")
    public ResponseEntity<BaseResponse<ClassTimeDTO>> getClassTimeMarket(
            @PathVariable("class_time_market_uuid") String classTimeMarketUuid
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classTimeMarketUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程时间市场UUID非法");
        }
        ClassTimeDTO classTimeMarket = curriculumService.getClassTimeMarket(classTimeMarketUuid);
        return ResultUtil.success("操作成功", classTimeMarket);
    }

    /**
     * 添加我的课程时间
     * <p>
     * 用于添加一个课程时间到我的课程时间中。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @PostMapping("/my-time/{class_time_market_uuid}")
    public ResponseEntity<BaseResponse<Void>> addMyClassTime(
            @PathVariable("class_time_market_uuid") String classTimeMarketUuid,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classTimeMarketUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程时间市场UUID非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.addMyClassTime(getUser, classTimeMarketUuid);
        return ResultUtil.success("操作成功");
    }

    /**
     * 删除我的课程时间
     * <p>
     * 用于删除一个课程时间从我的课程时间中。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @DeleteMapping("/my-time/{class_time_market_uuid}")
    public ResponseEntity<BaseResponse<Void>> deleteMyClassTime(
            @PathVariable("class_time_market_uuid") String classTimeMarketUuid,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classTimeMarketUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程时间市场UUID非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.deleteMyClassTime(getUser, classTimeMarketUuid);
        return ResultUtil.success("操作成功");
    }

    /**
     * 获取我的课程时间列表
     * <p>
     * 用于获取我的课程时间列表。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link ClassTimeDTO}>>
     */
    @HasAuthorize
    @GetMapping("/my-time")
    public ResponseEntity<BaseResponse<CustomPage<ClassTimeDTO>>> getMyClassTimeList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @NotNull HttpServletRequest request
    ) {
        UserDTO getUser = userService.getUserByToken(request);
        Page<ClassTimeMarketDO> myClassTimeList = curriculumService.getMyClassTimeList(getUser, page, size);
        return this.classTimeMarkCustomPage(myClassTimeList);
    }

    /**
     * 获取我的课程时间
     * <p>
     * 用于获取一个课程时间的详细信息。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link ClassTimeDTO}>>
     */
    @HasAuthorize
    @GetMapping("/my-time/{class_time_market_uuid}")
    public ResponseEntity<BaseResponse<ClassTimeDTO>> getMyClassTime(
            @PathVariable("class_time_market_uuid") String classTimeMarketUuid,
            @RequestHeader
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classTimeMarketUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程时间市场UUID非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        ClassTimeDTO myClassTime = curriculumService.getMyClassTime(getUser, classTimeMarketUuid);
        return ResultUtil.success("操作成功", myClassTime);
    }

    /**
     * 添加课程
     * <p>
     * 用于添加一个课程到我的课程时间中。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @PostMapping("/class")
    public ResponseEntity<BaseResponse<Void>> addClass(
            @RequestBody @Validated ClassVO classVO,
            @NotNull HttpServletRequest request
    ) {
        if (classVO.getStartTick() < 0) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "开始节数非法");
        }
        if (classVO.getEndTick() < 0 || classVO.getEndTick() < classVO.getStartTick()) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "结束节数非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.addClass(getUser, classVO);
        return ResultUtil.success("操作成功");
    }

    /**
     * 移动课程
     * <p>
     * 用于移动一个课程到指定周数以及节次位置
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @PatchMapping("/class/{class_uuid}")
    public ResponseEntity<BaseResponse<Void>> moveClass(
            @PathVariable("class_uuid") String classUuid,
            @RequestParam(value = "week", defaultValue = "0") Short week,
            @RequestParam(value = "start_tick", defaultValue = "0") Short startTick,
            @RequestParam(value = "end_tick", defaultValue = "0") Short endTick,
            @RequestParam(value = "day_tick", defaultValue = "0") Short dayTick,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程UUID非法");
        }
        if (week < 1 || week > 53) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "周数非法");
        }
        if (startTick < 0) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "开始节数非法");
        }
        if (endTick < 0 || endTick < startTick) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "结束节数非法");
        }
        if (dayTick < 0 || dayTick >= 7) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "星期数非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.moveClass(getUser, classUuid, week, startTick, endTick, dayTick);
        return null;
    }

    /**
     * 移动多个课程
     * <p>
     * 用于移动多个课程到指定周数以及节次位置
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @PutMapping("/class")
    public ResponseEntity<BaseResponse<Void>> moveMutiClass(
            @RequestParam("class_grade") String classGrade,
            @RequestParam("class_name") String className,
            @RequestParam(value = "original_start_tick", defaultValue = "0") Short originalStartTick,
            @RequestParam(value = "original_end_tick", defaultValue = "0") Short originalEndTick,
            @RequestParam(value = "original_day_tick", defaultValue = "0") Short originalDayTick,
            @RequestParam(value = "start_tick", defaultValue = "0") Short startTick,
            @RequestParam(value = "end_tick", defaultValue = "0") Short endTick,
            @RequestParam(value = "day_tick", defaultValue = "0") Short dayTick,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classGrade)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程表UUID非法");
        }
        if (className.isBlank()) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程名称非法");
        }
        if (originalStartTick < 0) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "原始开始节数非法");
        }
        if (originalEndTick < 0 || originalEndTick < originalStartTick) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "原始结束节数非法");
        }
        if (originalDayTick < 0 || originalDayTick > 7) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "原始星期数非法");
        }
        if (startTick < 0) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "开始节数非法");
        }
        if (endTick < 0 || endTick < startTick) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "结束节数非法");
        }
        if (dayTick < 0 || dayTick > 7) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "星期数非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.moveMutiClass(getUser, classGrade, className, originalDayTick, originalStartTick, originalEndTick, startTick, endTick, dayTick);
        return ResultUtil.success("操作成功");
    }

    /**
     * 删除课程
     * <p>
     * 用于删除一个课程。
     *
     * @return {@link ResponseEntity}<{@link BaseResponse}<{@link Void}>>
     */
    @HasAuthorize
    @DeleteMapping("/class/{class_uuid}")
    public ResponseEntity<BaseResponse<Void>> deleteClass(
            @PathVariable("class_uuid") String classUuid,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^[a-f0-9]{32}$", classUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "课程UUID非法");
        }
        UserDTO getUser = userService.getUserByToken(request);
        curriculumService.deleteClass(getUser, classUuid);
        return ResultUtil.success("操作成功");
    }

    @NotNull
    private ResponseEntity<BaseResponse<CustomPage<ClassTimeDTO>>> classTimeMarkCustomPage(Page<ClassTimeMarketDO> myClassTimeList) {
        CustomPage<ClassTimeDTO> newMyClassTimeList = new CustomPage<>();
        CopyUtil.pageDoCopyToDTO(myClassTimeList, newMyClassTimeList, ClassTimeDTO.class);
        myClassTimeList.getRecords().forEach(myClassTimeDTO -> {
            List<ClassTimeAbleDTO> classTimeAbleList = gson.fromJson(myClassTimeDTO.getTimetable(), new TypeToken<>() {
            }.getType());
            newMyClassTimeList.getRecords().forEach(newMyClassTimeDTO -> {
                if (newMyClassTimeDTO.getClassTimeMarketUuid().equals(myClassTimeDTO.getClassTimeMarketUuid())) {
                    newMyClassTimeDTO.setTimetable(classTimeAbleList);
                }
            });
        });
        return ResultUtil.success("操作成功", newMyClassTimeList);
    }
}
