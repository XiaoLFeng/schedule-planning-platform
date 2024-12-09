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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.schedule.model.dto.GroupDTO;
import com.xlf.schedule.model.dto.ScheduleDTO;
import com.xlf.schedule.model.dto.SchedulePriorityDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.entity.GroupDO;
import com.xlf.schedule.model.entity.ScheduleDO;
import com.xlf.schedule.model.vo.GroupVO;
import com.xlf.schedule.model.vo.ScheduleAddVO;
import com.xlf.schedule.model.vo.ScheduleEditVO;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 日程服务接口
 * <p>
 * 该接口是日程服务接口，用于定义日程相关的服务方法
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
public interface ScheduleService {

    /**
     * 创建小组
     * <p>
     * 该方法用于创建小组
     *
     * @param userDTO 用户信息
     * @param groupVO 创建小组请求参数
     * @return 小组uuid
     */
    String createGroup(UserDTO userDTO, GroupVO groupVO);

    /**
     * 编辑小组
     * <p>
     * 该方法用于编辑小组
     *
     * @param userDTO   用户信息
     * @param groupUuid 小组uuid
     * @param groupVO   编辑小组请求参数
     */
    void editGroup(UserDTO userDTO, String groupUuid, GroupVO groupVO);

    /**
     * 删除小组
     * <p>
     * 该方法用于删除小组
     *
     * @param userDTO   用户信息
     * @param groupUuid 小组uuid
     */
    void deleteGroup(UserDTO userDTO, String groupUuid);

    /**
     * 转让小组
     * <p>
     * 该方法用于转让小组
     *
     * @param userDTO   用户信息
     * @param groupUuid 小组uuid
     * @param newMaster 新队长
     */
    void transferMaster(UserDTO userDTO, String groupUuid, String newMaster);

    /**
     * 获取小组列表
     * <p>
     * 该方法用于获取小组列表
     *
     * @param userDTO 用户信息
     * @param type    类型
     * @param page    页码
     * @param size    每页大小
     * @param search  搜索关键字
     * @return 小组列表
     */
    Page<GroupDO> getGroupList(UserDTO userDTO, String type, Integer page, Integer size, String search);

    /**
     * 获取小组
     * <p>
     * 该方法用于获取小组
     *
     * @param userDTO   用户信息
     * @param groupUuid 小组uuid
     * @return 小组
     */
    GroupDTO getGroup(UserDTO userDTO, String groupUuid);

    /**
     * 添加小组成员
     * <p>
     * 该方法用于添加小组成员
     *
     * @param userDTO    用户信息
     * @param groupUuid  小组uuid
     * @param memberUuid 成员uuid
     */
    void addGroupMember(UserDTO userDTO, String groupUuid, String memberUuid);

    /**
     * 添加小组成员列表
     * <p>
     * 该方法用于添加小组成员列表
     *
     * @param userDTO        用户信息
     * @param groupUuid      小组uuid
     * @param memberUuidList 成员uuid列表
     */
    void addGroupMemberList(UserDTO userDTO, String groupUuid, List<String> memberUuidList);

    /**
     * 删除小组成员
     * <p>
     * 该方法用于删除小组成员
     *
     * @param userDTO    用户信息
     * @param groupUuid  小组uuid
     * @param memberUuid 成员uuid
     */
    void deleteGroupMember(UserDTO userDTO, String groupUuid, String memberUuid);

    /**
     * 获取小组成员列表
     * <p>
     * 该方法用于获取小组成员列表
     *
     * @param userDTO       用户信息
     * @param scheduleAddVO 日程请求参数
     */
    void addSchedule(UserDTO userDTO, ScheduleAddVO scheduleAddVO);

    /**
     * 编辑日程
     * <p>
     * 该方法用于编辑日程
     *
     * @param userDTO        用户信息
     * @param scheduleUuid   日程uuid
     * @param scheduleEditVO 编辑日程请求参数
     */
    void editSchedule(UserDTO userDTO, String scheduleUuid, ScheduleEditVO scheduleEditVO);

    /**
     * 删除日程
     * <p>
     * 该方法用于删除日程
     *
     * @param userDTO      用户信息
     * @param scheduleUuid 日程uuid
     */
    void deleteSchedule(UserDTO userDTO, String scheduleUuid);

    /**
     * 获取日程列表
     * <p>
     * 该方法用于获取日程列表
     *
     * @param userDTO      用户信息
     * @param scheduleUuid 日程uuid
     * @return 日程列表
     */
    ScheduleDTO getSchedule(UserDTO userDTO, String scheduleUuid);

    /**
     * 获取日程列表
     * <p>
     * 该方法用于获取日程列表
     *
     * @param userDTO 用户信息
     * @param page    页码
     * @param size    每页大小
     * @param search  搜索关键字
     * @return 日程列表
     */
    Page<ScheduleDO> getScheduleList(UserDTO userDTO, Integer page, Integer size, String search);

    /**
     * 获取日程优先级列表
     * <p>
     * 该方法用于获取日程优先级列表
     *
     * @param userDTO  用户信息
     * @param timeline 时间线
     * @return 日程优先级列表
     */
    SchedulePriorityDTO getSchedulePriorityList(UserDTO userDTO, @NotNull String timeline);

    /**
     * 获取日程列表
     * <p>
     * 该方法用于获取日程列表
     *
     * @param userDTO 用户信息
     * @param groupUuid 小组uuid
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日程列表
     */
    List<ScheduleDTO> getScheduleListMaybeGroup(UserDTO userDTO, String groupUuid, String startTime, String endTime);
}
