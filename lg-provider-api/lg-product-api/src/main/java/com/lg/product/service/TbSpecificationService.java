package com.lg.product.service;

import com.lg.product.model.domain.TbSpecification;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbSpecificationService extends IService<TbSpecification> {


         List<Map> selectOptionList();

}
