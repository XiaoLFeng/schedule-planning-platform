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
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.vo.GroupVO;
import com.xlf.schedule.service.ScheduleService;
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

import java.util.regex.Pattern;

/**
 * 日程控制器
 * <p>
 * 该类是日程控制器类，用于处理日程相关的请求
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final UserService userService;
    private final ScheduleService scheduleService;

    /**
     * 创建日程小组
     * <p>
     * 该方法用于创建日程小组，创建成功后，可以在小组中创建日程，小组的日程由小组成员共享
     *
     * @return 创建日程结果
     */
    @HasAuthorize
    @PostMapping("/group")
    public ResponseEntity<BaseResponse<Void>> createGroup(
            @RequestBody @Validated GroupVO groupVO,
            @NotNull HttpServletRequest request
    ) {
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.createGroup(userDTO, groupVO);
        return ResultUtil.success("创建小组成功");
    }

    /**
     * 编辑日程小组
     * <p>
     * 该方法用于编辑日程小组，编辑成功后，可以在小组中创建日程，小组的日程由小组成员共享
     *
     * @return 编辑日程结果
     */
    @HasAuthorize
    @PutMapping("/group/{group_uuid}")
    public ResponseEntity<BaseResponse<Void>> editGroup(
            @PathVariable("group_uuid") String groupUuid,
            @RequestBody @Validated GroupVO groupVO,
            @NotNull HttpServletRequest request
    ) {
        if (Pattern.compile("^[a-f0-9]{32}$").matcher(groupUuid).matches()) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "小组标识有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.editGroup(userDTO, groupUuid,  groupVO);
        return ResultUtil.success("编辑小组成功");
    }

    /**
     * 删除日程小组
     * <p>
     * 该方法用于删除日程小组，删除成功后，小组中的日程也会被删除
     *
     * @return 删除日程结果
     */
    @HasAuthorize
    @DeleteMapping("/group/{group_uuid}")
    public ResponseEntity<BaseResponse<Void>> deleteGroup(
            @PathVariable("group_uuid") String groupUuid,
            @NotNull HttpServletRequest request
    ) {
        if (Pattern.compile("^[a-f0-9]{32}$").matcher(groupUuid).matches()) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "小组标识有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.deleteGroup(userDTO, groupUuid);
        return ResultUtil.success("删除小组成功");
    }
}
