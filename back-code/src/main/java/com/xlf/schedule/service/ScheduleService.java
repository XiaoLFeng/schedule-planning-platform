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
import com.xlf.schedule.model.vo.GroupVO;

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
     */
    void createGroup(UserDTO userDTO, GroupVO groupVO);

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
}
