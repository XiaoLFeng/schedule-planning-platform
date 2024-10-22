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

import com.xlf.schedule.annotations.CheckAccessToYourOwnUuidOrAdminUuid;
import com.xlf.schedule.annotations.DataWrangling;
import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.vo.UserEditVO;
import com.xlf.schedule.service.MailService;
import com.xlf.schedule.service.UserService;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.annotations.HasAuthorize;
import com.xlf.utility.annotations.HasRole;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * <p>
 * 该类用于定义用户控制器;
 * 该类使用 {@link RestController} 注解标记;
 * 该类使用 {@link RequestMapping} 注解标记;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MailService mailService;

    /**
     * 获取当前用户
     * <p>
     * 该方法用于获取当前用户; 根据用户uuid获取用户信息;
     * 非管理员只能获取自己的信息, 管理员可以获取任何用户的信息。
     *
     * @param userUuid 用户uuid
     * @return 用户信息
     */
    @HasAuthorize
    @DataWrangling
    @CheckAccessToYourOwnUuidOrAdminUuid
    @GetMapping("/current/{user_uuid}")
    public ResponseEntity<BaseResponse<UserDTO>> userCurrent(
            @PathVariable("user_uuid") String userUuid
    ) {
        UserDTO getUser = userService.getUserByUuid(userUuid);
        return ResultUtil.success("获取成功", getUser);
    }

    /**
     * 编辑用户
     * <p>
     * 该方法用于编辑用户; 根据用户uuid编辑用户信息;
     * 非管理员只能编辑自己的信息, 管理员可以编辑任何用户的信息。
     *
     * @param userUuid   用户uuid
     * @param userEditVO 用户编辑信息
     * @param isAdmin    是否为管理员
     * @return 编辑结果
     */
    @HasAuthorize
    @CheckAccessToYourOwnUuidOrAdminUuid
    @PutMapping("/edit/{user_uuid}")
    public ResponseEntity<BaseResponse<Void>> userEdit(
            @PathVariable("user_uuid") String userUuid,
            @RequestBody @Validated UserEditVO userEditVO,
            @NotNull Boolean isAdmin
    ) {
        if (userEditVO.getUsername() == null && userEditVO.getPhone() == null && userEditVO.getEmail() == null) {
            throw new IllegalDataException(ErrorCode.BODY_MISSING);
        }
        userService.verifyEditVoData(userEditVO, isAdmin);
        UserDTO getUser = userService.getUserByUuid(userUuid);
        if (!isAdmin) {
            if (!mailService.verifyMailCode(getUser.getEmail(), userEditVO.getEmailCode())) {
                throw new BusinessException("邮箱验证码错误", ErrorCode.OPERATION_DENIED);
            }
        }
        userService.editUser(userUuid, userEditVO);
        return ResultUtil.success("修改成功");
    }

    /**
     * 封禁用户
     * <p>
     * 该方法用于封禁用户; 根据用户uuid封禁用户;
     * 只有管理员可以封禁用户。
     *
     * @param userUuid 用户uuid
     * @param reason   封禁原因
     * @param isBan    是否封禁
     * @return 封禁结果
     */
    @HasRole({"ADMIN"})
    @PatchMapping("/ban/{user_uuid}")
    public ResponseEntity<BaseResponse<Void>> userBan(
            @PathVariable("user_uuid") String userUuid,
            @RequestParam String reason,
            @RequestParam("ban") boolean isBan
    ) {
        userService.banUser(userUuid, isBan, reason);
        return ResultUtil.success("操作成功");
    }

    /**
     * 启用用户
     * <p>
     * 该方法用于启用用户; 根据用户uuid启用用户;
     * 只有管理员可以启用用户。
     *
     * @param userUuid 用户uuid
     * @param isEnable 是否启用
     * @return 启用结果
     */
    @HasRole({"ADMIN"})
    @PatchMapping("/enable/{user_uuid}")
    public ResponseEntity<BaseResponse<Void>> userEnable(
            @PathVariable("user_uuid") String userUuid,
            @RequestParam("enable") boolean isEnable
    ) {
        userService.enableUser(userUuid, isEnable);
        return ResultUtil.success("操作成功");
    }
}
