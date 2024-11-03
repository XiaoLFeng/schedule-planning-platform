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

import com.xlf.schedule.constant.PatternConstant;
import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.dto.UserFriendListDTO;
import com.xlf.schedule.service.FriendService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 好友控制器
 * <p>
 * 该类是好友控制器类，用于实现好友相关的控制器方法
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/friend")
@RequiredArgsConstructor
public class FriendController {
    private final UserService userService;
    private final FriendService friendService;

    /**
     * 添加好友
     * <p>
     * 该方法用于添加好友
     *
     * @return 添加好友结果
     */
    @HasAuthorize
    @PostMapping("/")
    public ResponseEntity<BaseResponse<Void>> addFriend(
            @RequestParam(value = "friend_uuid", defaultValue = "") String friendUuid,
            @RequestParam(value = "remark", defaultValue = "", required = false) String remark,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches(PatternConstant.UUID, friendUuid)) {
            throw new IllegalDataException(ErrorCode.PARAMETER_ILLEGAL, "用户主键有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        friendService.addFriend(userDTO, friendUuid, remark);
        return ResultUtil.success("好友申请已发送");
    }

    /**
     * 删除好友
     * <p>
     * 该方法用于删除好友
     *
     * @return 删除好友结果
     */
    @HasAuthorize
    @DeleteMapping("/")
    public ResponseEntity<BaseResponse<Void>> deleteFriend(
            @RequestParam(value = "friend_uuid", defaultValue = "") String friendUuid,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches(PatternConstant.UUID, friendUuid)) {
            throw new IllegalDataException(ErrorCode.PARAMETER_ILLEGAL, "用户主键有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        friendService.deleteFriend(userDTO, friendUuid);
        return ResultUtil.success("好友已删除");
    }

    /**
     * 同意好友
     * <p>
     * 该方法用于同意好友
     *
     * @return 同意好友结果
     */
    @HasAuthorize
    @GetMapping("/allow")
    public ResponseEntity<BaseResponse<Void>> allowFriend(
            @RequestParam(value = "friend_uuid", defaultValue = "") String friendUuid,
            @RequestParam(value = "allow", defaultValue = "false") Boolean allow,
            @RequestParam(value = "remark", defaultValue = "", required = false) String remark,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches(PatternConstant.UUID, friendUuid)) {
            throw new IllegalDataException(ErrorCode.PARAMETER_ILLEGAL, "用户主键有误");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        friendService.allowFriend(userDTO, friendUuid, allow, remark);
        return ResultUtil.success("好友已添加");
    }

    /**
     * 查询好友
     * <p>
     * 该方法用于查询用户还没有添加为好友用于查询好友的操作
     *
     * @return 查询好友结果
     */
    @HasAuthorize
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<UserFriendListDTO>>> searchFriend(
            @RequestParam(value = "search", defaultValue = "") String search,
            @NotNull HttpServletRequest request
    ) {
        if (!Pattern.matches("^([0-9A-Za-z-_@]+)$", search)) {
            throw new IllegalDataException(ErrorCode.PARAMETER_ILLEGAL, "搜索内容不合法");
        }
        UserDTO userDTO = userService.getUserByToken(request);
        List<UserFriendListDTO> userList = friendService.searchFriend(userDTO, search);
        return ResultUtil.success("搜索成功", userList);
    }

    /**
     * 获取好友列表
     * <p>
     * 该方法用于获取好友列表
     *
     * @return 获取好友列表结果
     */
    @HasAuthorize
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<UserFriendListDTO>>> getFriendList(
            @NotNull HttpServletRequest request
    ) {
        UserDTO userDTO = userService.getUserByToken(request);
        List<UserFriendListDTO> userList = friendService.getFriendList(userDTO);
        return ResultUtil.success("获取好友列表成功", userList);
    }
}
