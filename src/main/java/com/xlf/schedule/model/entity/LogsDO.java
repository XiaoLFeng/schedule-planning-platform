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
 * 日志数据表实体
 * <p>
 * 该类用于定义日志数据表实体;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Data
@TableName("xf_logs")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LogsDO {
    /**
     * 日志UUID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String logUuid;
    /**
     * 日志类型
     */
    private Short type;
    /**
     * 业务
     */
    private String business;
    /**
     * 用户
     */
    private String user;
    /**
     * 值
     */
    private String value;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}