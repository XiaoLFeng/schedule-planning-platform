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
import com.xlf.schedule.exception.lib.IllegalDataException;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.vo.UserEditVO;
import com.xlf.schedule.service.MailService;
import com.xlf.schedule.service.UserService;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.annotations.HasAuthorize;
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

    @HasAuthorize
    @CheckAccessToYourOwnUuidOrAdminUuid
    @GetMapping("/current/{userUuid}")
    public ResponseEntity<BaseResponse<UserDTO>> userCurrent(
            @PathVariable String userUuid
    ) {
        UserDTO getUser = userService.getUserByUuid(userUuid);
        return ResultUtil.success("获取成功", getUser);
    }

    @HasAuthorize
    @CheckAccessToYourOwnUuidOrAdminUuid
    @PutMapping("/edit/{userUuid}")
    public ResponseEntity<BaseResponse<Void>> userEdit(
            @PathVariable String userUuid,
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
}
