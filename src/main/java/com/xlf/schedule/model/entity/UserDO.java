package com.xlf.schedule.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户表实体
 * <p>
 * 该类用于定义用户表实体;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Data
@TableName("xf_user")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {
    /**
     * 用户表主键
     */
    @TableId(type = IdType.NONE)
    private String uuid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户当前密码
     */
    private String password;
    /**
     * 用户旧密码
     */
    private String oldPassword;
    /**
     * 角色
     */
    private String role;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 修改时间
     */
    private LocalDateTime updatedAt;
    /**
     * 用户是否开启
     */
    private Boolean enable;
    /**
     * 封禁到
     */
    private LocalDateTime bannedAt;
}