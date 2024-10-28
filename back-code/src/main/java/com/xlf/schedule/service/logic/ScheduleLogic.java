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

import com.google.gson.Gson;
import com.xlf.schedule.dao.GroupDAO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.entity.GroupDO;
import com.xlf.schedule.model.vo.GroupVO;
import com.xlf.schedule.service.RoleService;
import com.xlf.schedule.service.ScheduleService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 日程逻辑
 * <p>
 * 该类是日程逻辑类，用于处理日程相关的逻辑
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Service
@RequiredArgsConstructor
public class ScheduleLogic implements ScheduleService {
    private final GroupDAO groupDAO;
    private final Gson gson;
    private final RoleService roleService;

    @Override
    public void createGroup(UserDTO userDTO, @NotNull GroupVO groupVO) {
        List<String> tags;
        if (groupVO.getTags() == null || groupVO.getTags().isEmpty()) {
            tags = new ArrayList<>();
        } else {
            tags = groupVO.getTags();
        }
        GroupDO newGroup = new GroupDO();
        newGroup
                .setName(groupVO.getName())
                .setTags(gson.toJson(tags))
                .setMaster(userDTO.getUuid());
        groupDAO.save(newGroup);
    }

    @Override
    public void editGroup(UserDTO userDTO, String groupUuid, GroupVO groupVO) {
        groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, groupUuid)
                .oneOpt()
                .ifPresentOrElse(groupDO -> {
                    if (!groupDO.getMaster().equals(userDTO.getUuid())) {
                        if (!roleService.checkRoleHasAdmin(userDTO.getUuid())) {
                            throw new BusinessException("您没有权限编辑", ErrorCode.OPERATION_DENIED);
                        }
                    }
                    List<String> tags;
                    if (groupVO.getTags() == null || groupVO.getTags().isEmpty()) {
                        tags = new ArrayList<>();
                    } else {
                        tags = groupVO.getTags();
                    }
                    groupDO
                            .setName(groupVO.getName())
                            .setTags(gson.toJson(tags));
                    groupDAO.updateById(groupDO);
                }, () -> {
                    throw new BusinessException("小组不存在", ErrorCode.NOT_EXIST);
                });
    }

    @Override
    public void deleteGroup(UserDTO userDTO, String groupUuid) {
        groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, groupUuid)
                .oneOpt()
                .ifPresentOrElse(groupDO -> {
                    if (!groupDO.getMaster().equals(userDTO.getUuid())) {
                        if (!roleService.checkRoleHasAdmin(userDTO.getUuid())) {
                            throw new BusinessException("您没有权限删除", ErrorCode.OPERATION_DENIED);
                        }
                    }
                    groupDAO.removeById(groupDO);
                }, () -> {
                    throw new BusinessException("小组不存在", ErrorCode.NOT_EXIST);
                });
    }
}
