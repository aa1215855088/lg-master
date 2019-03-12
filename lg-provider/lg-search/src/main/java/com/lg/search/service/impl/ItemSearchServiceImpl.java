package com.lg.search.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lg.product.model.domain.TbTypeTemplate;
import com.lg.product.model.dto.BrandDTO;
import com.lg.product.model.dto.SpecDTO;
import com.lg.product.service.TbTypeTemplateService;
import com.lg.search.dto.ItemSearch;
import com.lg.search.repository.ItemSearchRepository;
import com.lg.search.service.ItemSearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.querydsl.QPageRequest;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Objects.requireNonNull;
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
 * @create: 2019-03-11 15:13
 **/
@Service(version = "1.0.0", timeout = 6000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private ItemSearchRepository itemSearchRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
//    @Reference(version = "1.0.0")
//    private TbTypeTemplateService tbTypeTemplateService;

    @Override
    public Map search(Map searchMap) {
        String keywords = (String) searchMap.get("keywords");
        if (keywords == null) {
            return null;
        }
        //1.查询列表
        Map<String, Object> map = new HashMap<>(requireNonNull(searchList(searchMap)));

        //2.分组查询 商品分类列表
//        List<String> categoryList = searchCategoryList(keywords);
//        map.put("categoryList", categoryList);
        //3.查询品牌和规格列表
//        String category = (String) searchMap.get("category");
//        if (!"".equals(category)) {
//            map.putAll(searchBrandAndSpecList(category) != null ? searchBrandAndSpecList(category) : null);
//        } else {
//            if (categoryList.size() > 0) {
//                map.putAll(searchBrandAndSpecList(categoryList.get(0)) != null ?
//                        searchBrandAndSpecList(categoryList.get(0)) : null);
//            }
//        }
        return map;
    }


    private List<String> searchCategoryList(String keywords) {
        Set<String> categorySet = new HashSet<>();
        Iterable<ItemSearch> search = this.itemSearchRepository.search(queryStringQuery(keywords));
        for (ItemSearch itemSearch : search) {
            categorySet.add(itemSearch.getCategory());
        }
        return new ArrayList<>(categorySet);
    }

    private Map<String, Object> searchList(Map searchMap) {
        Map<String, Object> map = new HashMap<String, Object>();


        Integer pageNo = (Integer) searchMap.get("pageNo");
        Integer pageSize = (Integer) searchMap.get("pageSize");
        String price = (String) searchMap.get("price");
        String brand = (String) searchMap.get("brand");
        String category = (String) searchMap.get("category");
        if (pageNo == null) {
            pageNo = 0;
        }
        if (pageSize == null) {
            pageSize = 40;
        }
        if (StrUtil.isNotBlank(price)) {

        }
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotBlank(brand)) {
            queryBuilder.must(matchQuery("brand", brand));
        }
        if (StrUtil.isNotBlank(category)) {
            queryBuilder.must(matchQuery("category", category));
        }
        if (StrUtil.isNotBlank(price)) {
            String[] split = price.split("-");
            if ("3000".equals(split[0])) {
                split[1] = "99999";
            }
            queryBuilder.must(rangeQuery("price").from(split[0]).to(split[1]));
        }
        queryBuilder.must(queryStringQuery((String) searchMap.get("keywords")));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightFields(new HighlightBuilder.Field("title").preTags("<em style='color:red'>")
                        .postTags("</em>"))
                .withPageable(new QPageRequest(pageNo - 1, pageSize))
                .build();
        String sortField = (String) searchMap.get("sortField");
        if (StrUtil.isNotBlank(sortField) && "price".equals(sortField)) {
            String sort = (String) searchMap.get("sort");
            if (StrUtil.isNotBlank(sort)) {
                if ("ASC".equals(sort)) {
                    searchQuery.addSort(Sort.by(sortField).ascending());
                } else {
                    searchQuery.addSort(Sort.by(sortField).descending());
                }
            }
        }

        AggregatedPage<ItemSearch> itemSearches = elasticsearchTemplate.queryForPage(searchQuery, ItemSearch.class,
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

        if (itemSearches == null) {
            return null;
        }
        Set<String> categorySet = new HashSet<>();
        Set<String> brandSet = new HashSet<>();
        Set<String> specSet = new HashSet<>();
        Iterable<ItemSearch> itemSearches1 = this.itemSearchRepository.search(queryStringQuery((String) searchMap.get(
                "keywords")));
        itemSearches1.forEach(itemSearch -> {
            categorySet.add(itemSearch.getCategory());
            brandSet.add(itemSearch.getBrand());
        });
        Page<ItemSearch> itemSearchPage = this.itemSearchRepository.search(searchQuery);
        map.put("rows", itemSearches.getContent());
        map.put("totalPages", itemSearchPage.getTotalPages());
        map.put("total", itemSearchPage.getTotalElements());
        map.put("categoryList", new ArrayList<>(categorySet));
        map.put("brandList", new ArrayList<>(brandSet));
        map.put("specList", new ArrayList<>(specSet));
        return map;

    }


//    private Map<String, Object> searchBrandAndSpecList(String category) {
//        Map<String, Object> map = new HashMap();
//        List<BrandDTO> brandList = this.tbTypeTemplateService.findBrandByName(category);
//        map.put("brandList", brandList);
//        List<SpecDTO> specList = this.tbTypeTemplateService.findSpecByName(category);
//        map.put("specList", specList);
//        return map;
//    }
}
