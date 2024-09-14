package com.xlf.schedule.service;

import com.xlf.schedule.model.vo.InitialSetupVO;
import org.jetbrains.annotations.NotNull;

/**
 * 初始化服务
 * <p>
 * 该类用于定义初始化服务;
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
public interface InitialService {
    /**
     * 设置初始化
     * <p>
     * 该方法用于设置初始化，初始化设置值对象；主要为创建超级管理员用户以及创建测试用户。
     *
     * @param initialSetupVO 初始化设置值对象
     */
    void setUp(@NotNull InitialSetupVO initialSetupVO);
}
