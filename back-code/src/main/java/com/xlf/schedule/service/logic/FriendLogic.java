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

package com.xlf.schedule.service.logic;

import com.xlf.schedule.dao.FriendDAO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.entity.FriendDO;
import com.xlf.schedule.service.FriendService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 好友逻辑
 * <p>
 * 该类是好友逻辑类，用于实现好友相关的逻辑方法
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Service
@RequiredArgsConstructor
public class FriendLogic implements FriendService {
    private final FriendDAO friendDAO;

    @Override
    public void addFriend(UserDTO userDTO, String friendUuid, String remark) {
        friendDAO.lambdaQuery().eq(FriendDO::getAllowerUserUuid, friendUuid)
                .oneOpt()
                .ifPresentOrElse(friendDO -> {
                    switch (friendDO.getIsFriend()) {
                        case 0 -> throw new BusinessException("正在等待对方同意", ErrorCode.OPERATION_DENIED);
                        case 1 -> throw new BusinessException("已经是好友", ErrorCode.OPERATION_DENIED);
                        default -> {
                            friendDO.setIsFriend(0);
                            friendDO.setSentAt(new Timestamp(System.currentTimeMillis()));
                            friendDAO.updateById(friendDO);
                        }
                    }
                }, () -> {
                    FriendDO friendDO = new FriendDO();
                    friendDO
                            .setSenderUserUuid(userDTO.getUuid())
                            .setAllowerUserUuid(friendUuid)
                            .setSenderRemarks(remark);
                    friendDAO.save(friendDO);
                });
    }

    @Override
    public void deleteFriend(@NotNull UserDTO userDTO, String friendUuid) {
        friendDAO.lambdaQuery()
                .or(i -> i.eq(FriendDO::getAllowerUserUuid, friendUuid).eq(FriendDO::getSenderUserUuid, userDTO.getUuid()))
                .or(i -> i.eq(FriendDO::getAllowerUserUuid, userDTO.getUuid()).eq(FriendDO::getSenderUserUuid, friendUuid))
                .oneOpt()
                .ifPresentOrElse(friendDAO::removeById, () -> {
                    throw new BusinessException("好友不存在", ErrorCode.OPERATION_DENIED);
                });
    }

    @Override
    public void allowFriend(@NotNull UserDTO userDTO, String friendUuid, Boolean isAllow, String remark) {
        friendDAO.lambdaQuery()
                .eq(FriendDO::getSenderUserUuid, friendUuid)
                .eq(FriendDO::getAllowerUserUuid, userDTO.getUuid())
                .oneOpt()
                .ifPresentOrElse(friendDO -> {
                    if (isAllow) {
                        friendDO.setIsFriend(1);
                    } else {
                        friendDO.setIsFriend(2);
                    }
                    friendDO.setAllowerRemarks(remark);
                    friendDAO.updateById(friendDO);
                }, () -> {
                    throw new BusinessException("好友申请不存在", ErrorCode.OPERATION_DENIED);
                });
    }
}
