package com.lg.task.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.model.domain.TbItem;
import com.lg.product.model.dto.Goods;
import com.lg.task.mapper.TbItemMapper;
import com.lg.task.repository.*;
import com.lg.task.service.IMailService;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPageImpl;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
 * @create: 2019-02-23 17:51
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class IMailServiceImplTest {
    @Autowired
    private IMailService iMailService;
    @Autowired
    private TemplateEngine templateEngine;//注入模板引擎
    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ItemSearchRepository itemSearchRepository;
    @Autowired
    private TbItemMapper tbItemMapper;

    @Test
    public void sendSimpleMail() throws MessagingException {
        String emailContent = templateEngine.process("msgTemplate", new Context());

        this.iMailService.sendHtmlMail("1013629501@qq.com", "徐子楼", emailContent);
    }

    @Test
    public void httpUtil() {
        String s = HttpRequest.get("http://localhost:9200/book/book/_search?q=name:红楼梦").execute().body();
        System.out.println(s);
    }

    @Test
    public void redisTest() {
        this.template.opsForValue().set("test", "123", 60000);
    }


    @Test
    public void findOne() {
        List<TbItem> tbItems = this.tbItemMapper.selectList(new QueryWrapper<>());
        List<ItemSearch> itemSearches = new ArrayList<>(1000);
        for (TbItem tbItem : tbItems) {
            ItemSearch itemSearch = new ItemSearch();
            BeanUtil.copyProperties(tbItem, itemSearch);
            Map map = JSON.parseObject(tbItem.getSpec(), Map.class);
//            itemSearch.setSpecMap(map);
            List<SpecDTO> list = new ArrayList<>();
            for (Object o : map.keySet()) {
                SpecDTO specDTO = new SpecDTO();
                specDTO.setText((String) o);
                specDTO.setOptions((String) map.get(o));
                list.add(specDTO);
            }
            itemSearch.setSpecList(list);
            itemSearches.add(itemSearch);
        }
        this.itemSearchRepository.saveAll(itemSearches);
    }

    @Test
    public void itemAll() {
        this.itemSearchRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void delete() {
        this.itemSearchRepository.deleteAll();
    }

    @Test
    public void elaticSearch() {
        Book book = new Book();
        book.setId(1);
        book.setName("红楼梦");
        book.setColor("红色");
        this.articleSearchRepository.save(book);
    }

    @Test
    public void search() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("红色", "name", "color"))
                .build();
        this.articleSearchRepository.search(searchQuery).stream().forEach(System.out::println);
    }

    @Test
    public void findByName() {
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withHighlightFields(null)
//                .build();
//        List<Book> books = elasticsearchTemplate.queryForList(searchQuery, Book.class);
//        for (Book book : books) {
//            System.out.println(book);
//        }
        this.articleSearchRepository.findBookByNameLike("红").stream().forEach(System.out::println);
    }


    @Test
    public void searchItem() {
        //使用queryStringQuery完成单字符串查询
        String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("sellerId", "yoyo"))
                .withHighlightFields(new HighlightBuilder.Field("sellerId").preTags(preTag).postTags(postTag))
                .build();
        elasticsearchTemplate.queryForList(searchQuery, ItemSearch.class).stream().forEach(System.out::println);
    }

    @Test
    public void hifhlight() {
        // 高亮字段
        String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";
//        QueryStringQueryBuilder builder=new QueryStringQueryBuilder();
//        QueryBuilder queryBuilder = QueryBuilders.nestedQuery("nested", QueryBuilders.termQuery("specList.text",
//                "机身内存"), ScoreMode.None);
//        QueryBuilder queryBuilder1 = QueryBuilders.nestedQuery("nested", QueryBuilders.termQuery("specList.options",
//                "8G"), ScoreMode.None);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder
                .must(matchQuery("title", "香蕉"));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
//                .withQuery(queryBuilder1)
                .withHighlightFields(new HighlightBuilder.Field("title").preTags(preTag).postTags(postTag))
                .withPageable(new QPageRequest(0, 10))
                .build();
        this.itemSearchRepository.search(searchQuery).forEach(System.out::println);
//        searchQuery.addSort(Sort.by("price").ascending());
////        searchQuery.addField s("brand");
//        AggregatedPage<ItemSearch> ideas = elasticsearchTemplate.queryForPage(searchQuery, ItemSearch.class,
//                new SearchResultMapper() {
//
//                    @Override
//                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
//                                                            Pageable pageable) {
//                        List<ItemSearch> chunk = new ArrayList<>();
//                        for (SearchHit searchHit : response.getHits()) {
////                            String brand = (String) searchHit.getSource().get("brand");
////                            if(!brand.equals("华为")){
////                                break;
////                            }
//                            if (response.getHits().getHits().length <= 0) {
//                                return null;
//                            }
//                            ItemSearch idea = new ItemSearch();
//                            //name or memoe
//                            HighlightField ideaTitle = searchHit.getHighlightFields().get("title");
//                            if (ideaTitle != null) {
//                                idea.setTitle(ideaTitle.fragments()[0].toString());
//                            } else {
//                                idea.setTitle((String) searchHit.getSource().get("title"));
//                            }
//                            idea.setSellerId((String) searchHit.getSource().get("sellerId"));
////                    idea.setId(Long.valueOf((String) searchHit.getSource().get("id")));
//                            idea.setPrice(BigDecimal.valueOf((Double) searchHit.getSource().get("price")));
//                            idea.setBrand((String) searchHit.getSource().get("brand"));
//                            idea.setCategory((String) searchHit.getSource().get("category"));
//                            idea.setImage((String) searchHit.getSource().get("image"));
////                            idea.setSpecMap((Map<String, Object>) searchHit.getSource().get("specMap"));
//                            chunk.add(idea);
//                        }
//                        if (chunk.size() > 0) {
//                            return new AggregatedPageImpl<>((List<T>) chunk);
//                        }
//                        return null;
//                    }
//                });
//
//        for (ItemSearch idea : ideas) {
//            System.out.println(idea);
//        }
    }

    @Test
    public void pageTest() {
        SearchQuery query =
                new NativeSearchQueryBuilder()
                        .withQuery(queryStringQuery("手机"))
                        .withPageable(new QPageRequest(0, 2))
                        .build();
        Page<ItemSearch> itemSearches = elasticsearchTemplate.queryForPage(query,
                ItemSearch.class,
                new SearchResultMapper() {
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
                                                            Pageable pageable) {
                        List<ItemSearch> chunk = new ArrayList<>();
                        for (SearchHit searchHit : response.getHits()) {
                            if (response.getHits().getHits().length <= 0) {
                                return null;
                            }
                            ItemSearch idea = new ItemSearch();
                            //name or memoe
                            HighlightField ideaTitle = searchHit.getHighlightFields().get("title");
                            if (ideaTitle != null) {
                                idea.setTitle(ideaTitle.fragments()[0].toString());
                            } else {
                                idea.setTitle((String) searchHit.getSource().get("title"));
                            }

                            idea.setSellerId((String) searchHit.getSource().get("sellerId"));
                            idea.setPrice(BigDecimal.valueOf((Double) searchHit.getSource().get("price")));
                            idea.setBrand((String) searchHit.getSource().get("brand"));
                            idea.setCategory((String) searchHit.getSource().get("category"));
                            idea.setImage((String) searchHit.getSource().get("image"));
                            idea.setGoodsId(Long.valueOf(searchHit.getSource().get("goodsId").toString()));
                            chunk.add(idea);
                        }
                        if (chunk.size() > 0) {
                            return new AggregatedPageImpl<>((List<T>) chunk);
                        }
                        return null;
                    }
                });
        System.out.println(itemSearches.getTotalElements());
        System.out.println(itemSearches.getNumberOfElements());
        System.out.println(itemSearches.getSize());
        System.out.println(itemSearches.getTotalPages());
    }


}
