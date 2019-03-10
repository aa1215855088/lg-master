package com.lg.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

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
 * @create: 2019-01-12 22:35
 **/
public class MyBatisPlusGenerator {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("E:\\javaweb\\项目实战\\lg-master\\lg-product\\src\\main\\java");//这里写你自己的java目录
        gc.setFileOverride(true);//是否覆盖
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("xuzilou");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("liu20000406");
        dsc.setUrl("jdbc:mysql://cdb-dzs2o6lc.cd.tencentcdb.com:10018/pinyougoudb");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setTablePrefix(new String[]{"pf_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略

       strategy.setInclude(new String[] { "t_mq_message_failed","t_mq_message_log"}); // 需要生成的表
        // 排除生成的表
//        strategy.setExclude(new String[]{"tb_pay_log", "tb_order_item", "tb_order",
//                "tb_user", "tb_content_category", "tb_content",
//                "tb_seller"});
        // 自定义实体父类
        strategy.setSuperEntityClass("com.lg.commons.core.mybatis.BaseEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        strategy.setSuperMapperClass("com.lg.commons.core.mybatis.MyMapper");
        // 自定义 com.lg.product.service.service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 com.lg.product.service.service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 com.lg.product.web.com.lg.product.web.controller 父类
        strategy.setSuperControllerClass("com.lg.commons.core.controller.BaseController");
        // 生成 RestController 风格
        strategy.setRestControllerStyle(true);
        strategy.setEntityLombokModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        // 注意不同的模块生成时要修改对应模块包名
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        pc.setEntity("com.lg.product.model.domain");
        pc.setMapper("com.lg.product.mapper");
        pc.setXml("mapper");
        pc.setService("com.lg.product.com.lg.product.service.service");
        pc.setServiceImpl("com.lg.product.com.lg.product.service.service.impl");
//        pc.setController("com.lg.product.web.com.lg.product.web.com.lg.product.web.controller");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/controller.java.vm");
        tc.setEntity("/templates/entity.java.vm");
        tc.setMapper("/templates/mapper.java.vm");
        tc.setXml("/templates/mapper.xml.vm");
        tc.setService("/templates/service.java.vm");
        tc.setServiceImpl("/templates/serviceImpl.java.vm");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
