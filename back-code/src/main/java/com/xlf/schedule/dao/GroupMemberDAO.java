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

package com.xlf.schedule.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.schedule.mapper.GroupMemberMapper;
import com.xlf.schedule.model.entity.GroupMemberDO;
import org.springframework.stereotype.Repository;

/**
 * 小组成员DAO
 * <p>
 * 该类是小组成员DAO类，用于处理小组成员相关的数据操作
 *
 * @version v1.0.0
 * @since v1.0.0
 * @author xiao_lfeng
 */
@Repository
public class GroupMemberDAO extends ServiceImpl<GroupMemberMapper, GroupMemberDO> implements IService<GroupMemberDO> {
}
