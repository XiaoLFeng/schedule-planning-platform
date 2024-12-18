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

import com.xlf.schedule.constant.StringConstant;
import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.schedule.model.dto.ListCurriculumDTO;
import com.xlf.schedule.model.dto.ListCurriculumTimeDTO;
import com.xlf.schedule.model.dto.ListUserDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.service.SelectListService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 选择列表控制器
 * <p>
 * 该类是选择列表控制器类，用于实现选择列表相关的控制器方法
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/select-list")
@RequiredArgsConstructor
public class SelectListController {
    private final SelectListService selectListService;
    private final UserService userService;

    /**
     * 查询用户列表
     * <p>
     * 该方法用于查询用户列表
     *
     * @return 用户列表
     */
    @HasAuthorize
    @GetMapping("/user")
    public ResponseEntity<BaseResponse<List<ListUserDTO>>> selectUserList(
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        if (!Pattern.matches("^(|[0-9A-Za-z-_@]+)$", search)) {
            throw new IllegalDataException(ErrorCode.PARAMETER_ILLEGAL, StringConstant.SEARCH_CONDITION_ILLEGAL);
        }
        List<ListUserDTO> listUser = selectListService.selectUserList(search);
        return ResultUtil.success("获取成功",  listUser);
    }

    /**
     * 查询课程列表
     * <p>
     * 该方法用于查询课程列表
     *
     * @return 课程列表
     */
    @HasAuthorize
    @GetMapping("/curriculum")
    public ResponseEntity<BaseResponse<List<ListCurriculumDTO>>> selectCurriculumList(
            @RequestParam(value = "search", defaultValue = "") String search,
            @NotNull HttpServletRequest request
    ) {
        if (search == null) {
            throw new IllegalDataException(ErrorCode.PARAMETER_ILLEGAL, StringConstant.SEARCH_CONDITION_ILLEGAL);
        }
        UserDTO userDTO = userService.getUserByToken(request);
        List<ListCurriculumDTO> listCurriculum = selectListService.selectCurriculumList(userDTO, search);
        return ResultUtil.success("获取成功",  listCurriculum);
    }

    /**
     * 查询课程时间列表
     * <p>
     * 该方法用于查询课程时间列表
     *
     * @return 课程时间列表
     */
    @HasAuthorize
    @GetMapping("/curriculum-my-time")
    public ResponseEntity<BaseResponse<List<ListCurriculumTimeDTO>>> selectCurriculumTimeList(
            @RequestParam(value = "search", defaultValue = "") String search,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^(|[0-9A-Za-z-_@]+)$", search)) {
            throw new IllegalDataException(ErrorCode.PARAMETER_ILLEGAL, StringConstant.SEARCH_CONDITION_ILLEGAL);
        }
        UserDTO userDTO = userService.getUserByToken(request);
        List<ListCurriculumTimeDTO> listCurriculumTime = selectListService.selectCurriculumTimeList(userDTO, search);
        return ResultUtil.success("获取成功",  listCurriculumTime);
    }
}
