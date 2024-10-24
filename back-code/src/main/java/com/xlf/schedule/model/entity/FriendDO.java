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

package com.xlf.schedule.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 好友表实体
 * <p>
 * 该类用于定义好友表实体;
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Data
@TableName("xf_friend")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FriendDO {

    /**
     * 好友主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String friendUuid;

    /**
     * 发送好友请求用户主键
     */
    private String senderUserUuid;

    /**
     * 接受好友请求用户主键
     */
    private String allowerUserUuid;

    /**
     * 发送方备注
     */
    private String senderRemarks;

    /**
     * 接收方备注
     */
    private String allowerRemarks;

    /**
     * 好友状态 (0: 等待审核, 1: 成为了好友, 2: 好友申请被拒绝)
     */
    private Integer isFriend;

    /**
     * 发送时间
     */
    private Timestamp sentAt;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;
}
