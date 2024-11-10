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

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xlf.schedule.constant.SystemConstant;
import com.xlf.schedule.dao.ClassGradeDAO;
import com.xlf.schedule.dao.ClassTimeMarketDAO;
import com.xlf.schedule.dao.ClassTimeMyDAO;
import com.xlf.schedule.dao.UserDAO;
import com.xlf.schedule.model.dto.ListCurriculumDTO;
import com.xlf.schedule.model.dto.ListCurriculumTimeDTO;
import com.xlf.schedule.model.dto.ListUserDTO;
import com.xlf.schedule.model.dto.UserDTO;
import com.xlf.schedule.model.dto.json.ClassTimeAbleDTO;
import com.xlf.schedule.model.entity.ClassGradeDO;
import com.xlf.schedule.model.entity.ClassTimeMarketDO;
import com.xlf.schedule.model.entity.ClassTimeMyDO;
import com.xlf.schedule.model.entity.UserDO;
import com.xlf.schedule.service.SelectListService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉列表逻辑
 * <p>
 * 该类是下拉列表逻辑类，用于实现下拉列表相关的逻辑方法
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Service
@RequiredArgsConstructor
public class SelectListLogic implements SelectListService {

    private final UserDAO userDAO;
    private final ClassGradeDAO classGradeDAO;
    private final ClassTimeMarketDAO classTimeMarketDAO;
    private final ClassTimeMyDAO classTimeMyDAO;
    private final Gson gson;

    @Override
    public List<ListUserDTO> selectUserList(String search) {
        List<ListUserDTO> newUserList = new ArrayList<>();
        LambdaQueryChainWrapper<UserDO> wrapper = userDAO.lambdaQuery()
                .like(UserDO::getUsername, search)
                .or()
                .like(UserDO::getPhone, search)
                .or()
                .like(UserDO::getEmail, search)
                .or()
                .like(UserDO::getUuid, search);
        List<UserDO> userList;
        if ("".equals(search)) {
            userList = wrapper.orderByDesc(UserDO::getCreatedAt).last("limit 20").list();
        } else {
            userList = wrapper.list();
        }
        userList.forEach(userDO -> {
            ListUserDTO newUser = new ListUserDTO();
            BeanUtils.copyProperties(userDO, newUser);
            newUserList.add(newUser);
        });
        return newUserList;
    }

    @Override
    public List<ListCurriculumDTO> selectCurriculumList(@NotNull UserDTO userDTO, String search) {
        return classGradeDAO.lambdaQuery()
                .eq(ClassGradeDO::getUserUuid, userDTO.getUuid())
                .and(i -> {
                    i.or(j -> j.like(ClassGradeDO::getUserUuid, search));
                    i.or(j -> j.like(ClassGradeDO::getNickname, search));
                }).list().stream().map(classGradeDO -> {
                    ListCurriculumDTO newCurriculum = new ListCurriculumDTO();
                    BeanUtils.copyProperties(classGradeDO, newCurriculum);
                    return newCurriculum;
                }).toList();
    }

    @Override
    public List<ListCurriculumTimeDTO> selectCurriculumTimeList(@NotNull UserDTO userDTO, String search) {
        List<String> stringList = classTimeMyDAO.lambdaQuery()
                .eq(ClassTimeMyDO::getUserUuid, userDTO.getUuid())
                .list().stream().map(ClassTimeMyDO::getTimeMarketUuid).toList();
        if (stringList.isEmpty()) {
            return classTimeMarketDAO.lambdaQuery()
                    .eq(ClassTimeMarketDO::getClassTimeMarketUuid, SystemConstant.defaultClassTimeUUID)
                    .list().stream().map(classTimeMarketDO -> {
                        ListCurriculumTimeDTO newCurriculumTime = new ListCurriculumTimeDTO();
                        BeanUtils.copyProperties(classTimeMarketDO, newCurriculumTime);
                        newCurriculumTime.setTimetable(gson.fromJson(classTimeMarketDO.getTimetable(), new TypeToken<ArrayList<ClassTimeAbleDTO>>() {}));
                        return newCurriculumTime;
                    }).toList();
        } else {
            return classTimeMarketDAO.lambdaQuery()
                    .in(ClassTimeMarketDO::getClassTimeMarketUuid, stringList)
                    .or()
                    .eq(ClassTimeMarketDO::getClassTimeMarketUuid, SystemConstant.defaultClassTimeUUID)
                    .list().stream().map(classTimeMarketDO -> {
                        ListCurriculumTimeDTO newCurriculumTime = new ListCurriculumTimeDTO();
                        BeanUtils.copyProperties(classTimeMarketDO, newCurriculumTime);
                        newCurriculumTime.setTimetable(gson.fromJson(classTimeMarketDO.getTimetable(), new TypeToken<ArrayList<ClassTimeAbleDTO>>() {}));
                        return newCurriculumTime;
                    }).toList();
        }
    }
}
