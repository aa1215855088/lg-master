
package com.lg.commons.core.mybatis;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * The interface My mapper.
 *
 * @param <T> the type parameter @author paascloud.net@gmail.com
 */
public interface MyMapper<T> extends BaseMapper<T> {

    /**
     * 查询所有
     * @return
     */
    List<T> findAll();
}
