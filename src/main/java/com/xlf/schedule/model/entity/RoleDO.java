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
 * 用户角色表实体
 * <p>
 * 该类用于定义用户角色表实体;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Data
@TableName("xf_role")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDO {
    /**
     * 用户角色表主键
     */
    @TableId(type = IdType.NONE)
    private String roleUuid;
    /**
     * 用户角色表名称
     */
    private String name;
    /**
     * 用户角色表显示名称
     */
    private String displayName;
    /**
     * 用户角色表描述
     */
    private String roleDesc;
    /**
     * 用户角色表创建时间
     */
    private LocalDateTime createdAt;
}