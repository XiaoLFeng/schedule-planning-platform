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

import com.xlf.schedule.model.dto.ListCurriculumDTO;
import com.xlf.schedule.model.dto.ListCurriculumTimeDTO;
import com.xlf.schedule.model.dto.ListUserDTO;
import com.xlf.schedule.model.dto.UserDTO;

import java.util.List;

/**
 * 下拉列表服务接口
 * <p>
 * 该接口是下拉列表服务接口，用于定义下拉列表相关的服务方法
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
public interface SelectListService {

    /**
     * 查询用户列表
     * <p>
     * 该方法用于查询用户列表
     *
     * @param search 搜索关键字
     * @return 用户列表
     */
    List<ListUserDTO> selectUserList(String search);

    /**
     * 查询课程列表
     * <p>
     * 该方法用于查询课程列表
     *
     * @param userDTO 用户信息
     * @param search  搜索关键字
     * @return 课程列表
     */
    List<ListCurriculumDTO> selectCurriculumList(UserDTO userDTO, String search);

    /**
     * 查询课程时间列表
     * <p>
     * 该方法用于查询课程时间列表
     *
     * @param userDTO 用户信息
     * @param search  搜索关键字
     * @return 课程时间列表
     */
    List<ListCurriculumTimeDTO> selectCurriculumTimeList(UserDTO userDTO, String search);
}
