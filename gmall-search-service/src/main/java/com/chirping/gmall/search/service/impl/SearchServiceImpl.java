package com.chirping.gmall.search.service.impl;

import java.io.IOException;
import java.lang.ref.Reference;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.chirping.gmall.pojo.PmsSearchParam;
import com.chirping.gmall.pojo.PmsSearchSkuInfo;
import com.chirping.gmall.pojo.PmsSkuAttrValue;
import com.chirping.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 刘志鹏
 * @date 2019/10/28
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) {
        //dsl
        String dslStr = getSearchDsl(pmsSearchParam);
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
        Search search = new Search.Builder(dslStr).addIndex("gmall1010").addType("PmsSkuInfo").build();
        SearchResult searchResult = null;
        try {
            searchResult = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            PmsSearchSkuInfo searchSkuInfo = hit.source;
            Map<String, List<String>> highlight = hit.highlight;
            if (highlight != null) {
                String skuName = highlight.get("skuName").get(0);
                searchSkuInfo.setSkuName(skuName);
            }
            pmsSearchSkuInfos.add(searchSkuInfo);
        }
        return pmsSearchSkuInfos;
    }

    private String getSearchDsl(PmsSearchParam pmsSearchParam) {

        String[] skuAttrValueList = pmsSearchParam.getValueId();
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();

        //jest的DSL工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (StringUtils.isNotEmpty(catalog3Id)) {
            //term
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", catalog3Id);
            //filter
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if (skuAttrValueList != null) {
            for (String pmsSkuAttrValue : skuAttrValueList) {
                //term
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", pmsSkuAttrValue);
                //filter
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        if (StringUtils.isNotEmpty(keyword)) {
            //WildcardQueryBuilder wildcardQueryBuilder = new WildcardQueryBuilder("skuName", "*" + keyword + "*");
            //match
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            //must
            boolQueryBuilder.must(matchQueryBuilder);
        }
        //query
        searchSourceBuilder.query(boolQueryBuilder);
        //highlight
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.sort("id", SortOrder.DESC);
        searchSourceBuilder.size(20);

        // aggs
        TermsAggregationBuilder groupby_attr = AggregationBuilders.terms("groupby_attr").field("skuAttrValueList.valueId");
        searchSourceBuilder.aggregation(groupby_attr);
        return searchSourceBuilder.toString();
    }
}
