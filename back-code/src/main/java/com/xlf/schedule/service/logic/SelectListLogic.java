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

import com.xlf.schedule.dao.UserDAO;
import com.xlf.schedule.model.dto.ListUserDTO;
import com.xlf.schedule.model.entity.UserDO;
import com.xlf.schedule.service.SelectListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉列表逻辑
 * <p>
 * 该类是下拉列表逻辑类，用于实现下拉列表相关的逻辑方法
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Service
@RequiredArgsConstructor
public class SelectListLogic implements SelectListService {

    private final UserDAO userDAO;

    @Override
    public List<ListUserDTO> selectUserList(String search) {
        List<ListUserDTO> newUserList = new ArrayList<>();
        userDAO.lambdaQuery()
                .like(UserDO::getUsername, search)
                .or()
                .like(UserDO::getPhone, search)
                .or()
                .like(UserDO::getEmail, search)
                .list()
                .forEach(userDO -> {
                    ListUserDTO newUser = new ListUserDTO();
                    BeanUtils.copyProperties(userDO, newUser);
                    newUserList.add(newUser);
                });
        return newUserList;
    }
}
