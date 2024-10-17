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

package com.xlf.schedule.service;

import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.entity.UserDO;
import com.xlf.schedule.model.vo.UserEditVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户服务接口
 * <br/>
 * 用户服务，用于用户相关操作。
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
public interface UserService {

    /**
     * 获取用户信息
     * <p>
     * 该方法用于获取用户信息；
     * 用户可查询条件有三种 {@code 手机号}、{@code 邮箱}、{@code 用户名}；
     * 查询成功用户后，返回用户信息。
     *
     * @param user 用户
     * @return {@link UserDO} 用户信息
     */
    UserDTO getUserForThreeType(String user);

    /**
     * 通过用户UUID获取用户信息
     * <p>
     * 该方法用于通过用户UUID获取用户信息；
     * 查询成功用户后，返回用户信息。
     *
     * @param userUuid 用户UUID
     * @return {@link UserDTO} 用户信息
     */
    UserDTO getUserByUuid(String userUuid);

    /**
     * 通过用户UUID获取用户信息
     * <p>
     * 该方法用于通过用户UUID获取用户信息；
     * 查询成功用户后，返回用户信息。
     *
     * @param request 请求
     * @return {@link UserDTO} 用户信息
     */
    UserDTO getUserByToken(HttpServletRequest request);

    /**
     * 通过邮箱获取用户信息
     * <p>
     * 该方法用于通过邮箱获取用户信息；
     * 查询成功用户后，返回用户信息。
     *
     * @param email 邮箱
     * @return {@link UserDTO} 用户信息
     */
    UserDTO getUserByEmail(String email);

    /**
     * 通过手机号获取用户信息
     * <p>
     * 该方法用于通过手机号获取用户信息；
     * 查询成功用户后，返回用户信息。
     *
     * @param phone 手机号
     * @return {@link UserDTO} 用户信息
     */
    UserDTO getUserByPhone(String phone);

    /**
     * 验证编辑用户数据
     * <p>
     * 该方法用于验证编辑用户数据；
     * 如果是管理员，则验证是否有权限编辑。
     *
     * @param userEditVO 用户编辑VO
     * @param isAdmin 是否是管理员
     */
    void verifyEditVoData(UserEditVO userEditVO, Boolean isAdmin);

    /**
     * 编辑用户
     * <p>
     * 该方法用于编辑用户；
     * 编辑成功后，返回用户信息。
     *
     * @param userUuid 用户UUID
     * @param userEditVO 用户编辑VO
     */
    void editUser(String userUuid, UserEditVO userEditVO);
}
