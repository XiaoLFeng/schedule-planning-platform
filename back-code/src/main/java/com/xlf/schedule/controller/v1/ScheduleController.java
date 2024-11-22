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
import com.xlf.schedule.constant.PatternConstant;
import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.schedule.model.CustomPage;
import com.xlf.schedule.model.dto.GroupDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.entity.GroupDO;
import com.xlf.schedule.model.vo.GroupMemberAddVO;
import com.xlf.schedule.model.vo.GroupVO;
import com.xlf.schedule.model.vo.ScheduleAddVO;
import com.xlf.schedule.model.vo.ScheduleEditVO;
import com.xlf.schedule.service.ScheduleService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final Gson gson;

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
        String groupUuid = scheduleService.createGroup(userDTO, groupVO);
        scheduleService.addGroupMember(userDTO, groupUuid, userDTO.getUuid());
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
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, groupUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "小组标识有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.editGroup(userDTO, groupUuid, groupVO);
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
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, groupUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "小组标识有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.deleteGroup(userDTO, groupUuid);
        return ResultUtil.success("删除小组成功");
    }

    /**
     * 转让日程小组
     * <p>
     * 该方法用于转让日程小组，转让成功后，新队长将拥有小组的管理权限
     *
     * @return 转让日程结果
     */
    @HasAuthorize
    @PatchMapping("/group/transfer/{group_uuid}")
    public ResponseEntity<BaseResponse<Void>> transferMaster(
            @PathVariable("group_uuid") String groupUuid,
            @RequestParam("new_master") String newMaster,
            @NotNull HttpServletRequest request
    ) {
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, groupUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "小组标识符有误");
        }
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, newMaster)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "新队长标识有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.transferMaster(userDTO, groupUuid, newMaster);
        return ResultUtil.success("转让队长成功");
    }

    /**
     * 获取日程小组列表
     * <p>
     * 该方法用于获取日程小组列表，获取成功后，可以查看小组的日程
     *
     * @return 获取日程列表结果
     */
    @HasAuthorize
    @GetMapping("/group")
    public ResponseEntity<BaseResponse<CustomPage<GroupDTO>>> getGroupList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "type", defaultValue = "master") String type,
            @NotNull HttpServletRequest request
    ) {
        if (!"master".equalsIgnoreCase(type) && !"all".equalsIgnoreCase(type) && !"join".equalsIgnoreCase(type)) {
            throw new IllegalDataException(ErrorCode.PARAMETER_ILLEGAL, "类型有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        Page<GroupDO> groupList = scheduleService.getGroupList(userDTO, type, page, size, search);
        CustomPage<GroupDTO> pageDTO = new CustomPage<>();
        CopyUtil.pageDoCopyToDTO(groupList, pageDTO, GroupDTO.class);
        groupList.getRecords().forEach(groupDO -> pageDTO.getRecords().forEach(groupDTO -> {
            if (groupDO.getGroupUuid().equals(groupDTO.getGroupUuid())) {
                String[] strings = gson.fromJson(groupDO.getTags(), String[].class);
                groupDTO.setTags(new ArrayList<>(Arrays.asList(strings)));
            }
        }));
        return ResultUtil.success("获取成功", pageDTO);
    }

    /**
     * 获取日程小组
     * <p>
     * 该方法用于获取日程小组
     *
     * @return 获取日程结果
     */
    @HasAuthorize
    @GetMapping("/group/{group_uuid}")
    public ResponseEntity<BaseResponse<GroupDTO>> getGroup(
            @PathVariable("group_uuid") String groupUuid,
            @NotNull HttpServletRequest request
    ) {
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, groupUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "小组标识符有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        GroupDTO groupDTO = scheduleService.getGroup(userDTO, groupUuid);
        return ResultUtil.success("获取成功", groupDTO);
    }

    /**
     * 批量添加日程小组成员
     * <p>
     * 该方法用于批量添加日程小组成员
     *
     * @return 添加日程成员结果
     */
    @HasAuthorize
    @PostMapping("/group/member")
    public ResponseEntity<BaseResponse<Void>> addGroupMemberList(
            @RequestBody @Validated GroupMemberAddVO groupMemberAddVO,
            @NotNull HttpServletRequest request
    ) {
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.addGroupMemberList(userDTO, groupMemberAddVO.getGroupUuid(), groupMemberAddVO.getUserUuid());
        return ResultUtil.success("添加成员成功");
    }

    /**
     * 添加日程小组成员
     * <p>
     * 该方法用于添加日程小组成员
     *
     * @return 添加日程成员结果
     */
    @HasAuthorize
    @PostMapping("/group/{group_uuid}/member/{member_uuid}")
    public ResponseEntity<BaseResponse<Void>> addGroupMember(
            @PathVariable("group_uuid") String groupUuid,
            @PathVariable("member_uuid") String memberUuid,
            @NotNull HttpServletRequest request
    ) {
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, groupUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "小组标识符有有误");
        }
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, memberUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "成员标识符有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.addGroupMember(userDTO, groupUuid, memberUuid);
        return ResultUtil.success("添加成员成功");
    }

    /**
     * 删除日程小组成员
     * <p>
     * 该方法用于删除日程小组成员
     *
     * @return 删除日程成员结果
     */
    @HasAuthorize
    @DeleteMapping("/group/{group_uuid}/member/{member_uuid}")
    public ResponseEntity<BaseResponse<Void>> deleteGroupMember(
            @PathVariable("group_uuid") String groupUuid,
            @PathVariable("member_uuid") String memberUuid,
            @NotNull HttpServletRequest request
    ) {
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, groupUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "小组标识符有有误");
        }
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, memberUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "成员标识符有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.deleteGroupMember(userDTO, groupUuid, memberUuid);
        return ResultUtil.success("删除成员成功");
    }

    /**
     * 添加日程
     * <p>
     * 该方法用于添加日程
     *
     * @return 添加日程结果
     */
    @HasAuthorize
    @PostMapping("/")
    public ResponseEntity<BaseResponse<Void>> addSchedule(
            @RequestBody @Validated ScheduleAddVO scheduleAddVO,
            @NotNull HttpServletRequest request
    ) {
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.addSchedule(userDTO, scheduleAddVO);
        return ResultUtil.success("添加日程成功");
    }

    /**
     * 编辑日程
     * <p>
     * 该方法用于编辑日程
     *
     * @return 编辑日程结果
     */
    @HasAuthorize
    @PutMapping("/")
    public ResponseEntity<BaseResponse<Void>> editSchedule(
            @RequestParam("schedule_uuid") String scheduleUuid,
            @RequestBody @Validated ScheduleEditVO scheduleEditVO,
            @NotNull HttpServletRequest request
    ) {
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, scheduleUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "日程标识符有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.editSchedule(userDTO, scheduleUuid, scheduleEditVO);
        return ResultUtil.success("编辑日程成功");
    }

    /**
     * 删除日程
     * <p>
     * 该方法用于删除日程
     *
     * @return 删除日程结果
     */
    @HasAuthorize
    @DeleteMapping("/")
    public ResponseEntity<BaseResponse<Void>> deleteSchedule(
            @RequestParam("schedule_uuid") String scheduleUuid,
            @NotNull HttpServletRequest request
    ) {
        if (Pattern.matches(PatternConstant.NO_DASH_UUID, scheduleUuid)) {
            throw new IllegalDataException(ErrorCode.BODY_INVALID, "日程标识符有误");
        }

        UserDTO userDTO = userService.getUserByToken(request);
        scheduleService.deleteSchedule(userDTO, scheduleUuid);
        return ResultUtil.success("删除日程成功");
    }
}
