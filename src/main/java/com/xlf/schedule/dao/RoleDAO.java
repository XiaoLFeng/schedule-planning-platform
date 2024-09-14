package com.xlf.schedule.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.schedule.mapper.RoleMapper;
import com.xlf.schedule.model.entity.RoleDO;
import org.springframework.stereotype.Repository;

/**
 * 用户角色表数据访问对象
 * <p>
 * 该类用于定义用户角色表数据访问对象;
 * 该类使用 {@link Repository} 注解标记;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Repository
public class RoleDAO extends ServiceImpl<RoleMapper, RoleDO> implements IService<RoleDO> {
}