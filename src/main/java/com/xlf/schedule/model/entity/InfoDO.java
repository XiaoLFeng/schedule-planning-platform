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
 * 信息表实体
 * <p>
 * 该类用于定义信息表实体;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Data
@TableName("xf_info")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class InfoDO {
    /**
     * 信息表主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String infoUuid;
    /**
     * 信息表键
     */
    private String key;
    /**
     * 信息表值
     */
    private String value;
    /**
     * 信息表更新时间
     */
    private LocalDateTime updatedAt;
}