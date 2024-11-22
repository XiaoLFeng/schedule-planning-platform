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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.xlf.schedule.constant.StringConstant;
import com.xlf.schedule.dao.GroupDAO;
import com.xlf.schedule.dao.GroupMemberDAO;
import com.xlf.schedule.dao.ScheduleDAO;
import com.xlf.schedule.dao.UserDAO;
import com.xlf.schedule.model.dto.GroupDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.entity.GroupDO;
import com.xlf.schedule.model.entity.GroupMemberDO;
import com.xlf.schedule.model.entity.ScheduleDO;
import com.xlf.schedule.model.entity.UserDO;
import com.xlf.schedule.model.vo.GroupVO;
import com.xlf.schedule.model.vo.ScheduleAddVO;
import com.xlf.schedule.model.vo.ScheduleEditVO;
import com.xlf.schedule.service.FileService;
import com.xlf.schedule.service.RoleService;
import com.xlf.schedule.service.ScheduleService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import com.xlf.utility.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 日程逻辑
 * <p>
 * 该类是日程逻辑类，用于处理日程相关的逻辑
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Service
@RequiredArgsConstructor
public class ScheduleLogic implements ScheduleService {
    private final GroupDAO groupDAO;
    private final Gson gson;
    private final RoleService roleService;
    private final GroupMemberDAO groupMemberDAO;
    private final UserDAO userDAO;
    private final FileService fileService;
    private final ScheduleDAO scheduleDAO;

    @Override
    public String createGroup(UserDTO userDTO, @NotNull GroupVO groupVO) {
        List<String> tags;
        if (groupVO.getTags() == null || groupVO.getTags().isEmpty()) {
            tags = new ArrayList<>();
        } else {
            tags = groupVO.getTags();
        }
        String newGroupUuid = UuidUtil.generateUuidNoDash();
        GroupDO newGroup = new GroupDO();
        newGroup
                .setGroupUuid(newGroupUuid)
                .setName(groupVO.getName())
                .setUserAbleAdd(groupVO.getAbleAdd())
                .setTags(gson.toJson(tags))
                .setMaster(userDTO.getUuid());
        groupDAO.save(newGroup);
        return newGroupUuid;
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
                            .setUserAbleAdd(groupVO.getAbleAdd())
                            .setTags(gson.toJson(tags));
                    groupDAO.updateById(groupDO);
                }, () -> {
                    throw new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST);
                });
    }

    @Override
    public void deleteGroup(UserDTO userDTO, String groupUuid) {
        groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, groupUuid)
                .oneOpt()
                .ifPresentOrElse(groupDO -> {
                    if (!groupDO.getMaster().equals(userDTO.getUuid())) {
                        if (!roleService.checkRoleHasAdmin(userDTO.getUuid())) {
                            throw new BusinessException(StringConstant.NO_PERMISSION_DELETE, ErrorCode.OPERATION_DENIED);
                        }
                    }
                    groupDAO.removeById(groupDO);
                }, () -> {
                    throw new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST);
                });
    }

    @Override
    public void transferMaster(UserDTO userDTO, String groupUuid, String newMaster) {
        groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, groupUuid)
                .oneOpt()
                .ifPresentOrElse(groupDO -> {
                    if (!groupDO.getMaster().equals(userDTO.getUuid())) {
                        if (!roleService.checkRoleHasAdmin(userDTO.getUuid())) {
                            throw new BusinessException("您没有权限转让", ErrorCode.OPERATION_DENIED);
                        }
                    }
                    groupDO.setMaster(newMaster);
                    groupDAO.updateById(groupDO);
                }, () -> {
                    throw new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST);
                });
    }

    @Override
    public Page<GroupDO> getGroupList(UserDTO userDTO, @NotNull String type, Integer page, Integer size, String search) {
        switch (type) {
            case "master":
                Page<GroupDO> groupPage;
                if (search != null && !search.isEmpty()) {
                    groupPage = groupDAO.lambdaQuery()
                            .or(i -> i.eq(GroupDO::getMaster, userDTO.getUuid()).like(GroupDO::getName, search))
                            .or(i -> i.eq(GroupDO::getMaster, userDTO.getUuid()).like(GroupDO::getTags, search))
                            .page(new Page<>(page, size));
                } else {
                    groupPage = groupDAO.lambdaQuery()
                            .eq(GroupDO::getMaster, userDTO.getUuid())
                            .page(new Page<>(page, size));
                }
                return groupPage;
            case "join":
                List<GroupMemberDO> groupMemberList = groupMemberDAO.lambdaQuery()
                        .eq(GroupMemberDO::getUserUuid, userDTO.getUuid())
                        .list().stream()
                        .filter(groupMemberDO -> {
                            GroupDO groupDO = groupDAO.lambdaQuery()
                                    .eq(GroupDO::getGroupUuid, groupMemberDO.getGroupUuid())
                                    .oneOpt()
                                    .orElseThrow(() -> new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST));
                            return !groupDO.getMaster().equals(userDTO.getUuid());
                        }).toList();
                List<GroupDO> groupList = groupMemberList.stream()
                        .filter(groupMemberDO -> search == null || groupDAO.lambdaQuery()
                                .eq(GroupDO::getGroupUuid, groupMemberDO.getGroupUuid())
                                .like(GroupDO::getName, search)
                                .or()
                                .eq(GroupDO::getGroupUuid, groupMemberDO.getGroupUuid())
                                .like(GroupDO::getTags, search)
                                .oneOpt().isPresent())
                        .map(groupMemberDO -> groupDAO.lambdaQuery()
                                .eq(GroupDO::getGroupUuid, groupMemberDO.getGroupUuid())
                                .oneOpt()
                                .orElseThrow(() -> new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST))
                        ).toList();
                Page<GroupDO> pageGroup = new Page<>(page, size);
                pageGroup.setRecords(groupList);
                return pageGroup;
            case "all":
                List<GroupMemberDO> groupMemberListAll = groupMemberDAO.lambdaQuery()
                        .eq(GroupMemberDO::getUserUuid, userDTO.getUuid())
                        .list();
                List<GroupDO> groupListAll = groupMemberListAll.stream()
                        .filter(groupMemberDO -> search == null || groupDAO.lambdaQuery()
                                .eq(GroupDO::getGroupUuid, groupMemberDO.getGroupUuid())
                                .like(GroupDO::getName, search)
                                .or()
                                .eq(GroupDO::getGroupUuid, groupMemberDO.getGroupUuid())
                                .like(GroupDO::getTags, search)
                                .oneOpt().isPresent())
                        .map(groupMemberDO -> groupDAO.lambdaQuery()
                                .eq(GroupDO::getGroupUuid, groupMemberDO.getGroupUuid())
                                .oneOpt()
                                .orElseThrow(() -> new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST))
                        ).toList();
                Page<GroupDO> pageGroupAll = new Page<>(page, size);
                pageGroupAll.setRecords(groupListAll);
                return pageGroupAll;
            default:
                throw new BusinessException("类型有误", ErrorCode.PARAMETER_ILLEGAL);
        }
    }

    @Override
    public GroupDTO getGroup(@NotNull UserDTO userDTO, String groupUuid) {
        GroupDO groupDO = groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, groupUuid).oneOpt()
                .orElseThrow(() -> new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST));
        if (!groupDO.getMaster().equals(userDTO.getUuid())) {
            groupMemberDAO.lambdaQuery()
                    .eq(GroupMemberDO::getGroupUuid, groupUuid)
                    .eq(GroupMemberDO::getUserUuid, userDTO.getUuid())
                    .oneOpt()
                    .orElseThrow(() -> new BusinessException(StringConstant.NOT_GROUP_MEMBER, ErrorCode.OPERATION_DENIED));
            if (!roleService.checkRoleHasAdmin(userDTO.getUuid())) {
                throw new BusinessException("您没有权限查看", ErrorCode.OPERATION_DENIED);
            }
        }
        GroupDTO groupDTO = new GroupDTO();
        BeanUtils.copyProperties(groupDO, groupDTO);
        String[] strings = gson.fromJson(groupDO.getTags(), String[].class);
        groupDTO.setTags(new ArrayList<>(List.of(strings)));
        return groupDTO;
    }

    @Override
    public void addGroupMember(UserDTO userDTO, String groupUuid, String memberUuid) {
        groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, groupUuid)
                .oneOpt()
                .ifPresentOrElse(groupDO -> {
                    if (!groupDO.getMaster().equals(userDTO.getUuid())) {
                        if (!roleService.checkRoleHasAdmin(userDTO.getUuid())) {
                            throw new BusinessException("您没有权限添加", ErrorCode.OPERATION_DENIED);
                        }
                    }
                    userDAO.lambdaQuery().eq(UserDO::getUuid, memberUuid)
                            .oneOpt()
                            .orElseThrow(() -> new BusinessException(StringConstant.USER_NOT_EXIST, ErrorCode.NOT_EXIST));
                    GroupMemberDO groupMemberDO = new GroupMemberDO();
                    groupMemberDO
                            .setGroupUuid(groupUuid)
                            .setUserUuid(memberUuid)
                            .setStatus((short) 1);
                    groupMemberDAO.save(groupMemberDO);
                }, () -> {
                    throw new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST);
                });
    }

    @Override
    public void addGroupMemberList(UserDTO userDTO, String groupUuid, @NotNull List<String> memberUuidList) {
        if (memberUuidList.isEmpty()) {
            throw new BusinessException("成员列表不能为空", ErrorCode.PARAMETER_ILLEGAL);
        }
        groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, groupUuid)
                .oneOpt()
                .ifPresentOrElse(groupDO -> {
                    if (!groupDO.getMaster().equals(userDTO.getUuid())) {
                        if (!roleService.checkRoleHasAdmin(userDTO.getUuid())) {
                            throw new BusinessException("您没有权限添加", ErrorCode.OPERATION_DENIED);
                        }
                    }
                    List<GroupMemberDO> groupMemberDOList = new ArrayList<>();
                    memberUuidList.forEach(memberUuid -> {
                        userDAO.lambdaQuery().eq(UserDO::getUuid, memberUuid)
                                .oneOpt()
                                .orElseThrow(() -> new BusinessException(StringConstant.USER_NOT_EXIST, ErrorCode.NOT_EXIST));
                        GroupMemberDO groupMemberDO = new GroupMemberDO();
                        groupMemberDO
                                .setGroupUuid(groupUuid)
                                .setUserUuid(memberUuid)
                                .setStatus((short) 1);
                        groupMemberDOList.add(groupMemberDO);
                    });
                    groupMemberDAO.saveBatch(groupMemberDOList);
                }, () -> {
                    throw new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST);
                });
    }

    @Override
    public void deleteGroupMember(UserDTO userDTO, String groupUuid, String memberUuid) {
        groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, groupUuid)
                .oneOpt()
                .ifPresentOrElse(groupDO -> {
                    if (!groupDO.getMaster().equals(userDTO.getUuid())) {
                        if (!roleService.checkRoleHasAdmin(userDTO.getUuid())) {
                            throw new BusinessException(StringConstant.NO_PERMISSION_DELETE, ErrorCode.OPERATION_DENIED);
                        }
                    }
                    groupMemberDAO.lambdaQuery()
                            .eq(GroupMemberDO::getGroupUuid, groupUuid)
                            .eq(GroupMemberDO::getUserUuid, memberUuid)
                            .oneOpt()
                            .ifPresentOrElse(groupMemberDAO::removeById, () -> {
                                throw new BusinessException("成员不存在", ErrorCode.NOT_EXIST);
                            });
                }, () -> {
                    throw new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST);
                });
    }

    @Override
    public void addSchedule(UserDTO userDTO, @NotNull ScheduleAddVO scheduleAddVO) {
        ScheduleDO newSchedule = new ScheduleDO();
        // 检查用户是否添加到小组内
        if (scheduleAddVO.getAddLocation()) {
            GroupDO getGroup = groupDAO.lambdaQuery()
                    .eq(GroupDO::getGroupUuid, scheduleAddVO.getGroupUuid())
                    .oneOpt()
                    .orElseThrow(() -> new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST));
            if (!getGroup.getUserAbleAdd()) {
                throw new BusinessException("小组不允许普通用户添加日程", ErrorCode.OPERATION_DENIED);
            }
            groupMemberDAO.lambdaQuery()
                    .eq(GroupMemberDO::getUserUuid, userDTO.getUuid())
                    .eq(GroupMemberDO::getGroupUuid, getGroup.getGroupUuid())
                    .oneOpt()
                    .orElseThrow(() -> new BusinessException(StringConstant.NOT_GROUP_MEMBER, ErrorCode.OPERATION_DENIED));
            newSchedule.setGroupUuid(scheduleAddVO.getGroupUuid());
        }
        // 图片上传
        if (scheduleAddVO.getResources() != null && !scheduleAddVO.getResources().isEmpty()) {
            ArrayList<String> imageNameList = new ArrayList<>();
            scheduleAddVO.getResources().forEach(resource -> imageNameList.add(fileService.uploadImage(resource)));
            newSchedule.setResources(gson.toJson(imageNameList));
        }
        // 添加日程
        if (newSchedule.getGroupUuid() == null) {
            newSchedule.setUserUuid(userDTO.getUuid());
        }
        newSchedule
                .setName(scheduleAddVO.getName())
                .setDescription(scheduleAddVO.getDescription())
                .setStartTime(scheduleAddVO.getStartTime())
                .setEndTime(scheduleAddVO.getEndTime())
                .setType(scheduleAddVO.getType())
                .setLoopType(scheduleAddVO.getLoopType())
                .setCustomLoop(scheduleAddVO.getCustomLoop())
                .setTags(gson.toJson(scheduleAddVO.getTags()))
                .setPriority(scheduleAddVO.getPriority());
        scheduleDAO.save(newSchedule);
    }

    @Override
    public void editSchedule(UserDTO userDTO, String scheduleUuid, ScheduleEditVO scheduleEditVO) {
        ScheduleDO scheduleDO = scheduleDAO.lambdaQuery().eq(ScheduleDO::getScheduleUuid, scheduleUuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("日程不存在", ErrorCode.NOT_EXIST));
        if (scheduleDO.getUserUuid() != null && !scheduleDO.getUserUuid().equals(userDTO.getUuid())) {
            throw new BusinessException("您没有权限编辑", ErrorCode.OPERATION_DENIED);
        }
        if (scheduleDO.getGroupUuid() != null) {
            GroupDO groupDO = groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, scheduleDO.getGroupUuid())
                    .oneOpt()
                    .orElseThrow(() -> new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST));
            if (!groupDO.getMaster().equals(userDTO.getUuid())) {
                groupMemberDAO.lambdaQuery()
                        .eq(GroupMemberDO::getUserUuid, userDTO.getUuid())
                        .eq(GroupMemberDO::getGroupUuid, groupDO.getGroupUuid())
                        .oneOpt()
                        .orElseThrow(() -> new BusinessException(StringConstant.NOT_GROUP_MEMBER, ErrorCode.OPERATION_DENIED));
            }
            scheduleDO.setUserUuid(null);
            scheduleDO.setGroupUuid(groupDO.getGroupUuid());
        }
        // 删除图片
        if (scheduleEditVO.getDeleteResources() != null && !scheduleEditVO.getDeleteResources().isEmpty()) {
            scheduleEditVO.getDeleteResources().forEach(fileService::deleteImage);
        }
        // 图片上传
        if (scheduleEditVO.getAddResources() != null && !scheduleEditVO.getAddResources().isEmpty()) {
            ArrayList<String> imageNameList = new ArrayList<>();
            scheduleEditVO.getAddResources().forEach(resource -> imageNameList.add(fileService.uploadImage(resource)));
            scheduleDO.setResources(gson.toJson(imageNameList));
        }
        if (scheduleEditVO.getGroupUuid() == null) {
            scheduleDO.setUserUuid(userDTO.getUuid());
            scheduleDO.setGroupUuid(null);
        }
        scheduleDO
                .setName(scheduleEditVO.getName())
                .setDescription(scheduleEditVO.getDescription())
                .setStartTime(scheduleEditVO.getStartTime())
                .setEndTime(scheduleEditVO.getEndTime())
                .setType(scheduleEditVO.getType())
                .setLoopType(scheduleEditVO.getLoopType())
                .setCustomLoop(scheduleEditVO.getCustomLoop())
                .setTags(gson.toJson(scheduleEditVO.getTags()))
                .setPriority(scheduleEditVO.getPriority())
                .setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        scheduleDAO.updateById(scheduleDO);
    }

    @Override
    public void deleteSchedule(UserDTO userDTO, String scheduleUuid) {
        ScheduleDO scheduleDO = scheduleDAO.lambdaQuery().eq(ScheduleDO::getScheduleUuid, scheduleUuid)
                .oneOpt()
                .orElseThrow(() -> new BusinessException("日程不存在", ErrorCode.NOT_EXIST));
        if (scheduleDO.getUserUuid() != null && !scheduleDO.getUserUuid().equals(userDTO.getUuid())) {
            throw new BusinessException(StringConstant.NO_PERMISSION_DELETE, ErrorCode.OPERATION_DENIED);
        }
        if (scheduleDO.getGroupUuid() != null) {
            GroupDO groupDO = groupDAO.lambdaQuery().eq(GroupDO::getGroupUuid, scheduleDO.getGroupUuid())
                    .oneOpt()
                    .orElseThrow(() -> new BusinessException(StringConstant.GROUP_NOT_EXIST, ErrorCode.NOT_EXIST));
            if (!groupDO.getMaster().equals(userDTO.getUuid())) {
                throw new BusinessException(StringConstant.NO_PERMISSION_DELETE, ErrorCode.OPERATION_DENIED);
            }
        }
        scheduleDAO.removeById(scheduleDO);
    }
}
