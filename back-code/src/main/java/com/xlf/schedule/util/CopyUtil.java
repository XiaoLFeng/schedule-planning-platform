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

package com.xlf.schedule.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.schedule.model.CustomPage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * 复制工具
 * <p>
 * 该类用于定义复制工具；
 * 该类包含以下字段：
 * 该类用于将源对象复制到目标对象。
 *
 * @author xiao_lfeng
 * @version v1.0.0
 * @since v1.0.0
 */
@Slf4j
public class CopyUtil {

    /**
     * 复制对象
     * <p>
     * 该方法用于将源对象复制到目标对象；
     * 复制对象时，需要提供 {@code 源对象}、{@code 目标对象}、{@code 目标对象类}；
     * 复制成功后，返回复制结果。
     *
     * @param source   源对象
     * @param target   目标对象
     * @param dtoClass 目标对象类
     * @param <T>      源对象类型
     * @param <E>      目标对象类型
     */
    public static <T, E> void pageDoCopyToDTO(
            @NotNull Page<T> source,
            @NotNull CustomPage<E> target,
            Class<E> dtoClass
    ) {
        target
                .setRecords(new ArrayList<>())
                .setTotal(source.getTotal())
                .setSize(source.getSize())
                .setCurrent(source.getCurrent())
                .setPages(source.getPages());
        if (!source.getRecords().isEmpty()) {
            source.getRecords().forEach(record -> {
                try {
                    E dto = dtoClass.getDeclaredConstructor().newInstance();
                    BeanUtils.copyProperties(record, dto);
                    target.getRecords().add(dto);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException("DTO 对象实例化失败: " + e.getMessage());
                }
            });
        }
    }
}
