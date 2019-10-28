package com.chirping.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.pojo.PmsSearchSkuInfo;
import com.chirping.gmall.pojo.PmsSkuInfo;
import com.chirping.gmall.service.SpuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchServiceApplicationTests {

    @Resource
    JestClient jestClient;
    @Reference
    SpuService spuService;

    @Test
    public void contextLoads() throws IOException {
        //jest的DSL工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //term
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("", "");
        //filter
        boolQueryBuilder.filter(termQueryBuilder);
        //match
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("", "");
        //must
        boolQueryBuilder.must(matchQueryBuilder);
        //query
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);

        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("gmall1010").addType("PmsSkuInfo").build();

        SearchResult searchResult = jestClient.execute(search);

        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);

        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            PmsSearchSkuInfo searchSkuInfo = hit.source;

            pmsSearchSkuInfos.add(searchSkuInfo);
        }

        System.out.println(pmsSearchSkuInfos);
    }

    @Test
    public void put() throws IOException {
        List<PmsSkuInfo> pmsSkuInfoList = new ArrayList<>();
        pmsSkuInfoList = spuService.getAllSku();
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(pmsSkuInfo, pmsSearchSkuInfo);
            pmsSearchSkuInfo.setId(Long.parseLong(pmsSkuInfo.getId()));
            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
        }
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            Index build = new Index.Builder(pmsSearchSkuInfo).index("gmall1010").type("PmsSkuInfo").id(pmsSearchSkuInfo.getId() + "").build();
            jestClient.execute(build);
        }
    }

}
