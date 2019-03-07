package com.lg.product.service;

import com.lg.product.model.domain.TbSpecificationOption;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbSpecificationOptionService extends IService<TbSpecificationOption> {
    void deleteSpecificationOptionBySpecId(Long[] id);

    void addSpecificationOption(TbSpecificationOption tbSpecificationOption);

    List<TbSpecificationOption> findOptionBySpecId(Long specId);

    void deleteOneOptionBySpecId(Long specHiddenId);
}
