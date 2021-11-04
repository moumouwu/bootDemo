package com.boot.demo.service;

import com.alibaba.fastjson.JSON;
import com.boot.demo.dao.UserRepository;
import com.boot.demo.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author binSin
 * @date 2021/9/3
 */
@Slf4j
@Service
public class SysUserServiceImpl {

    @Resource
    private ElasticsearchRestTemplate restTemplate;

    @Resource
    UserRepository userRepository;

    /**
     * @param searchMap key:
     *                  category     商品分类的过滤查询
     *                  <p>
     *                  brand        商品品牌的过滤查询   TCL
     *                  <p>
     *                  spec_规格的名称   value  规格的值      规格相关
     *                  <p>
     *                  price           value 价格区间的字符串 0-500   3000-*
     *                  <p>
     *                  <p>
     *                  pageNum   当前的页码
     *                  pageSize  不用传递   (写死)
     *                  <p>
     *                  <p>
     *                  sortField  要排序的字段  price
     *                  sortRule   要排序的规则 (ASC DESC)
     * @return
     */
    public Map search(Map<String, String> searchMap) {
        //1.获取到关键字
        String keywords = searchMap.get("nickname");

        //2.判断是否为空 如果 为空 给一个默认 值:华为
        if (StringUtils.isEmpty(keywords)) {
            keywords = "张";
        }
        //3.创建 查询构建对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //4.设置 查询的条件

        // 4.1 商品分类的列表展示: 按照商品分类的名称来分组
        //terms  指定分组的一个别名
        //field 指定要分组的字段名
        //size 指定查询结果的数量 默认是10个
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuCategorygroup").field("username").size(50));

        //4.2 商品的品牌的列表展示  按照商品品牌来进行分组
//        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrandgroup").field("brandName").size(100));

        //4.3 商品的规格的列表展示   按照商品的规格的字段spec 进行分组
        //规则 要求 字段 是一个keyword类型的  spec.keyword
//        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpecgroup").field("spec.keyword").size(500));

        //========================高亮条件设置 开始=====================================
        //4.4 设置高亮的字段 设置前缀 和 后缀
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 设置高亮的字段 针对 商品的名称进行高亮
        highlightBuilder.field("nickname");
        // 设置前缀 和 后缀
        highlightBuilder.preTags("<em style=\"color:red\">").postTags("</em>");
        //========================高亮条件设置 开始=====================================


        //匹配查询  先分词 再查询  主条件查询
        //参数1 指定要搜索的字段
        //参数2 要搜索的值(先分词 再搜索)
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("nickname", keywords));
        //从多个字段中搜索数据
//        nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(keywords,"name","categoryName","brandName"));


        //========================过滤查询条件 开始=====================================
        BoolQueryBuilder boolQueryBuilder = getBoolQuery(searchMap);
        //========================过滤查询条件 结束=====================================

        //========================分页查询条件 开始=====================================
        // 第一个参数:指定当前的页码  注意: 如果是第一页 数值为0
        // 第二个参数:指定当前的页的显示的行
        String pageNum1 = searchMap.get("pageNum");
        Integer pageNum = Integer.valueOf(pageNum1);
        Integer pageSize = 30;
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        //========================分页查询条件 结束=====================================


        //========================排序操作条件 开始=====================================
        // 排序操作
        // 获取排序的字段 和要排序的规则
        String sortField = searchMap.get("sortField");//price
        String sortRule = searchMap.get("sortRule");//DESC ASC
        FieldSortBuilder sortBuilder = null;
        FieldSortBuilder order = null;
        if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)) {
            //执行排序
            sortBuilder = SortBuilders.fieldSort(sortField).order(sortRule.equalsIgnoreCase("ASC") ? SortOrder.ASC : SortOrder.DESC);
            order = SortBuilders.fieldSort(sortField).order(SortOrder.valueOf(sortRule));
        }
        //========================排序操作条件 结束=====================================
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder
                // 高亮设置
                .withHighlightBuilder(highlightBuilder)
                // 过滤查询
                .withFilter(boolQueryBuilder)
                // 分页查询
                .withPageable(pageRequest)
                // 排序查询  nativeSearchQueryBuilder.withSort(order);
                //        nativeSearchQueryBuilder.withSort(sortBuilder);
                //5.构建查询对象(封装了查询的语法)
                .build();


        //6.执行查询
//        restTemplate.queryForPage(nativeSearchQuery, SysUser.class, new SearchResultMapperImpl());
        SearchHits<SysUser> searchHits = restTemplate.search(nativeSearchQuery, SysUser.class);

        //6.1 查询总数
        long count = restTemplate.count(nativeSearchQuery, SysUser.class);
        // 最终结果集
        List<SysUser> sysUsers = new ArrayList<>();
        for (SearchHit<SysUser> searchHit : searchHits) {
            // 获取普通结果集
            SysUser content = searchHit.getContent();

            // 获取高亮结果集
            List<String> nameList = searchHit.getHighlightField("nickname");
            if (nameList.size() > 0 && !nameList.isEmpty()) {
                String nameHigh = nameList.get(0);
                content.setNickname(nameHigh);
            }
            log.error("结果集:{}", JSON.toJSONString(content));
            sysUsers.add(content);
        }
        // 6.2 获取聚合分组结果  获取商品分类的列表数据
        Aggregations aggregations = searchHits.getAggregations();
        List<String> userNameList = new ArrayList<>();
        Terms stringTermsCategory = aggregations.get("skuCategorygroup");
        for (Terms.Bucket bucket : stringTermsCategory.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            userNameList.add(keyAsString);
        }
//        List<String> categoryList = getStringsCategoryList(stringTermsCategory);
//        System.out.println(skuCategorygroup.getMetaData().isEmpty());
//        StringTerms stringTermsCategory = (StringTerms) skuInfos.getAggregation("skuCategorygroup");
//        List<String> categoryList = getStringsCategoryList(stringTermsCategory);


        //6.3 获取 品牌分组结果 列表数据

//        StringTerms stringTermsBrand = (StringTerms) skuInfos.getAggregation("skuBrandgroup");
//        List<String> brandList = getStringsBrandList(stringTermsBrand);

        //6.4 获取 规格的分组结果 列表数据map
//        StringTerms stringTermsSpec = (StringTerms) skuInfos.getAggregation("skuSpecgroup");
//        Map<String, Set<String>> specMap = getStringSetMap(stringTermsSpec);

        //7.获取结果  返回map

//        List<SkuInfo> content = skuInfos.getContent();//当前的页的集合
//        int totalPages = skuInfos.getTotalPages();//总页数
//        long totalElements = skuInfos.getTotalElements();//总记录数

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("categoryList", sysUsers);//商品分类的列表数据
//        resultMap.put("brandList", brandList);   //商品品牌的列表数据
//        resultMap.put("specMap", specMap);   //商品规格的列表数据展示
//        resultMap.put("rows", content);
//        resultMap.put("total", totalElements);
        resultMap.put("userNameList", userNameList);
        resultMap.put("totalPages", count);
        resultMap.put("pageNum", pageNum);
        resultMap.put("pageSize", pageSize);
        return resultMap;
    }

    /**
     * 构建bool查询
     *
     * @param searchMap
     * @return
     */
    private BoolQueryBuilder getBoolQuery(Map<String, String> searchMap) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //4.4 过滤查询的条件设置   商品分类的条件
//        String category = searchMap.get("category");
//
//        if(!StringUtils.isEmpty(category)) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("categoryName", category));
//        }
        //4.5 过滤查询的条件设置   商品品牌的条件
//        String brand = searchMap.get("brand");
//
//        if(!StringUtils.isEmpty(brand)) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("brandName", brand));
//        }

        //4.6 过滤查询的条件设置   规格条件

//        if(searchMap!=null){
//            for (String key : searchMap.keySet()) {//{ brand:"",category:"",spec_网络:"电信4G"}
//                if(key.startsWith("spec_"))  {
//                    //截取规格的名称
//                    boolQueryBuilder.filter(QueryBuilders.termQuery("specMap."+key.substring(5)+".keyword", searchMap.get(key)));
//                }
//            }
//        }
        //4.7 过滤查询的条件设置   价格区间的过滤查询
//        String price = searchMap.get("price");// 0-500  3000-*
//        if(!StringUtils.isEmpty(price)){
//            //获取值 按照- 切割
//            String[] split = price.split("-");
//            //过滤范围查询
//            //0<=price<=500
//            if(!split[1].equals("*")) {
//                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").from(split[0], true).to(split[1], true));
//            }else{
//                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(split[0]));
//            }
//
//        }
        return boolQueryBuilder;
    }

    private Map<String, Set<String>> getStringSetMap(StringTerms stringTermsSpec) {
        //key :规格的名称
        //value :规格名称对应的选项的多个值集合set
        Map<String, Set<String>> specMap = new HashMap<String, Set<String>>();
        Set<String> specValues = new HashSet<String>();
        if (stringTermsSpec != null) {
            //1. 获取分组的结果集
            for (StringTerms.Bucket bucket : stringTermsSpec.getBuckets()) {
                //2.去除结果集的每一行数据()   {"手机屏幕尺寸":"5.5寸","网络":"电信4G","颜色":"白","测试":"s11","机身内存":"128G","存储":"16G","像素":"300万像素"}
                String keyAsString = bucket.getKeyAsString();

                //3.转成JSON 对象  map  key :规格的名称  value:规格名对应的选项的单个值
                Map<String, String> map = JSON.parseObject(keyAsString, Map.class);
                for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                    String key = stringStringEntry.getKey();//规格名称   手机屏幕尺寸
                    String value = stringStringEntry.getValue();//规格的名称对应的单个选项值 5.5寸

                    //先从原来的specMap中 获取 某一个规格名称 对应的规格的选项值集合
                    specValues = specMap.get(key);
                    if (specValues == null) {
                        specValues = new HashSet<>();
                    }
                    specValues.add(value);
                    //4.提取map中的值放入到返回的map中
                    specMap.put(key, specValues);
                }
            }
        }
        return specMap;
    }

    private List<String> getStringsBrandList(StringTerms stringTermsBrand) {
        List<String> brandList = new ArrayList<>();
        if (stringTermsBrand != null) {
            for (StringTerms.Bucket bucket : stringTermsBrand.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();//品牌的名称 huawei
                brandList.add(keyAsString);
            }
        }
        return brandList;
    }

    /**
     * 获取分组结果   商品分类的分组结果
     *
     * @param stringTermsCategory
     * @return
     */
    private List<String> getStringsCategoryList(StringTerms stringTermsCategory) {
        List<String> categoryList = new ArrayList<>();
        if (stringTermsCategory != null) {
            for (StringTerms.Bucket bucket : stringTermsCategory.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();
                System.out.println(keyAsString);//就是商品分类的数据
                categoryList.add(keyAsString);
            }
        }
        return categoryList;
    }


    /**
     * 导入到索引库
     */
    public void save() {
//        //1.调用 goods微服务的fegin 查询 符合条件的sku的数据
//        List<SysUser> data = skuResult.getData();//sku的列表
//        //将sku的列表 转换成es中的skuinfo的列表
//        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(data), SkuInfo.class);
//        for (SkuInfo skuInfo : skuInfos) {
//            //获取规格的数据
//
//            //转成MAP  key: 规格的名称  value:规格的选项的值
//            Map<String, Object> map = JSON.parseObject(skuInfo.getSpec(), Map.class);
//            skuInfo.setSpecMap(map);
//        }
//
//
//        // 2.调用spring data elasticsearch的API 导入到ES中
//        skuEsMapper.saveAll(skuInfos);
        String str = "{\"电视音响效果\":\"立体声\",\"电视屏幕尺寸\":\"20英寸\",\"尺码\":\"165\"}";
//        SysUser user = new SysUser();
//        user.setId(2);
//        user.setNickname("张三2");
//        user.setUsername("admin");
//        user.setSpecMap(JSON.parseObject(str, Map.class));
//        userRepository.save(user);

        List<SysUser> list = new ArrayList<>();
        for (int i = 20; i < 200; i++) {
            SysUser user1 = new SysUser();
            user1.setId(i);
            user1.setNickname("张三" + i + "李四");
            user1.setUsername("admin" + i);
            user1.setSpecMap(JSON.parseObject(str, Map.class));
            list.add(user1);
        }
        userRepository.saveAll(list);
    }


}
