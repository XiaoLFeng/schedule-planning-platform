package com.xlf.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlf.schedule.model.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表映射器
 * <p>
 * 该类用于定义用户表映射器;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}