package com.lg.product.service.impl;


import com.lg.product.model.vo.GoodsVO;
import com.lg.product.service.TbGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @program: lg-master
 * @description:
 * @author: 徐子楼
 * @create: 2019-03-01 18:09
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbGoodsServiceImplTest {
    @Autowired
    private TbGoodsService tbGoodsService;

    @Test
    public void test1(){
         GoodsVO goodsVO=new GoodsVO();
         goodsVO.setAuditStatus("0");
         goodsVO.setGoodsName("狗");
        this.tbGoodsService.search(1,2,goodsVO).getResult().getRows().stream().forEach(System.out::println);
    }

    @Test
    public void test2(){
        tbGoodsService.findAll().stream().forEach(System.out::println);
    }
}
