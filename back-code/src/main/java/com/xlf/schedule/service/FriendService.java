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
import com.xlf.schedule.model.dto.UserFriendListDTO;

import java.util.List;

/**
 * 好友服务接口
 * <p>
 * 该接口是好友服务接口，用于定义好友相关的服务方法
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
public interface FriendService {

    /**
     * 添加好友
     * <p>
     * 该方法用于添加好友
     *
     * @param userDTO    用户信息
     * @param friendUuid 好友uuid
     * @param remark     备注
     */
    void addFriend(UserDTO userDTO, String friendUuid, String remark);

    /**
     * 删除好友
     * <p>
     * 该方法用于删除好友
     *
     * @param userDTO    用户信息
     * @param friendUuid 好友uuid
     */
    void deleteFriend(UserDTO userDTO, String friendUuid);

    /**
     * 允许好友
     * <p>
     * 该方法用于允许好友
     *
     * @param userDTO    用户信息
     * @param friendUuid 好友uuid
     * @param isAllow    是否允许
     * @param remark     备注
     */
    void allowFriend(UserDTO userDTO, String friendUuid, Boolean isAllow, String remark);

    /**
     * 查询好友
     * <p>
     * 该方法用于查询用户还没有添加为好友用于查询好友的操作
     *
     * @param userDTO 用户信息
     * @param search  搜索关键字
     */
    List<UserFriendListDTO> searchFriend(UserDTO userDTO, String search);

    /**
     * 获取好友列表
     * <p>
     * 该方法用于获取好友列表
     *
     * @param userDTO 用户信息
     */
    List<UserFriendListDTO> getFriendList(UserDTO userDTO);

    /**
     * 获取好友申请列表
     * <p>
     * 该方法用于获取好友申请列表
     *
     * @param userDTO 用户信息
     */
    List<UserFriendListDTO> getFriendApplicationList(UserDTO userDTO);

    /**
     * 获取好友待审核列表
     * <p>
     * 该方法用于获取好友待审核列表
     *
     * @param userDTO 用户信息
     */
    List<UserFriendListDTO> getFriendPendingReviewList(UserDTO userDTO);

    /**
     * 获取好友已拒绝列表
     * <p>
     * 该方法用于获取好友已拒绝列表
     *
     * @param userDTO 用户信息
     */
    List<UserFriendListDTO> getFriendDeniedList(UserDTO userDTO);
}
