package com.lg.task.repository;

import com.lg.product.model.domain.TbGoods;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

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
 * @create: 2019-03-11 09:24
 **/
public interface ArticleSearchRepository extends ElasticsearchRepository<Book, Integer> {

    @Override
    Iterable<Book> findAll();

    @Query("q=name")
    List<Book> findBookByName(String name);

    @Override
    Iterable<Book> search(QueryBuilder queryBuilder);

    List<Book> findBookByNameLike(String name);
}
