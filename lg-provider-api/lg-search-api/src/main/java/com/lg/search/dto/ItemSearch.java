package com.lg.search.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @create: 2019-03-11 10:34
 **/
@Data
@Document(indexName = "items", type = "item")
public class ItemSearch implements Serializable {

    private static final long serialVersionUID = 6860498895598023307L;
    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long goodsId;
    @Field(type = FieldType.Keyword, searchAnalyzer = "ik_max_word", analyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Float)
    private BigDecimal price;

    @Field(type = FieldType.Text)
    private String image;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String sellerId;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Text)
    private String spec;
    @Field(type = FieldType.Nested)
    private Map<String, Object> specMap;
}
