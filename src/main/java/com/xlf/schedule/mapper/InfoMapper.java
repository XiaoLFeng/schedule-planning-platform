package com.xlf.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlf.schedule.model.entity.InfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 信息表Mapper
 * <p>
 * 该类用于定义信息表Mapper。
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Mapper
public interface InfoMapper extends BaseMapper<InfoDO> {
}
