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

import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.schedule.model.dto.ClassGradeDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.vo.ClassGradeVO;
import com.xlf.schedule.service.CurriculumService;
import com.xlf.schedule.service.UserService;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.annotations.HasAuthorize;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            if (!classGradeVO.getSemesterEnd().isBlank()) {
                endTime = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(classGradeVO.getSemesterEnd()).getTime());
            }
            if (endTime != null && !endTime.before(startTime)) {
                log.debug(endTime.toString());
                throw new IllegalDataException(ErrorCode.BODY_ILLEGAL, "结束时间不能早于开始时间");
            }
            UserDTO getUser = userService.getUserByToken(request);
            String classGradeUuid = curriculumService.createClassGrade(
                    classGradeVO.getGradeName(),
                    startTime,
                    endTime,
                    getUser.getUuid()
            );
            ClassGradeDTO classGrade = curriculumService.getClassGrade(classGradeUuid);
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
        try {
            UserDTO getUser = userService.getUserByToken(request);
            curriculumService.editClassGrade(
                    classGradeUuid,
                    classGradeVO.getGradeName(),
                    new Date(new SimpleDateFormat("yyyy-MM-dd").parse(classGradeVO.getSemesterBegin()).getTime()),
                    new Date(new SimpleDateFormat("yyyy-MM-dd").parse(classGradeVO.getSemesterEnd()).getTime()),
                    getUser
            );
            return ResultUtil.success("操作成功");
        } catch (ParseException e) {
            throw new IllegalDataException(ErrorCode.BODY_ILLEGAL);
        }
    }
}
