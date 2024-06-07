package cn.m1yellow.mypages.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class EsUtil {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 关键字
     */
    public static final String KEYWORD = ".keyword";


    /**
     * 获取低水平客户端
     */
    public RestClient getLowLevelClient() {
        return restHighLevelClient.getLowLevelClient();
    }

    /**
     * 创建索引
     */
    public boolean createIndex(String index) throws IOException {
        if (isIndexExist(index)) {
            log.error("Index is exits!");
            return false;
        }
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        //2.执行客户端请求
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

        log.info("创建索引{}成功", index);

        return response.isAcknowledged();
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String index) throws IOException {
        if (!isIndexExist(index)) {
            log.error("Index is not exits!");
            return false;
        }
        //删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        //执行客户端请求
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);

        log.info("删除索引{}成功", index);

        return delete.isAcknowledged();
    }


    /**
     * 判断索引是否存在
     */
    public boolean isIndexExist(String index) throws IOException {

        GetIndexRequest request = new GetIndexRequest(index);

        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);

        return exists;
    }


    /**
     * 数据添加，指定id
     */
    public String addData(JSONObject jsonObject, String index, String id) throws IOException {

        //创建请求
        IndexRequest request = new IndexRequest(index);
        //规则 put /test_index/_doc/1
        request.id(id);
        request.timeout(TimeValue.timeValueSeconds(5));
        //将数据放入请求 json
        IndexRequest source = request.source(jsonObject, XContentType.JSON);
        //客户端发送请求
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info("添加数据成功 索引为: {}, response 状态: {}, id为: {}", index, response.status().getStatus(), response.getId());
        return response.getId();
    }


    /**
     * 数据添加 随机id
     */
    public String addData(JSONObject jsonObject, String index) throws IOException {
        return addData(jsonObject, index, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    /**
     * 批量添加
     */
    public boolean addDataBatch(List<Object> dataList, String index) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        for (int i = 0; i < dataList.size(); i++) {
            bulkRequest.add(new IndexRequest(index).id((i + 1) + "").source(JSON.toJSONString(dataList.get(i)), XContentType.JSON));
        }
        BulkResponse bulkRes = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        return bulkRes.hasFailures();
    }

    /**
     * 通过ID删除数据
     */
    public void deleteDataById(String index, String id) throws IOException {
        //删除请求
        DeleteRequest request = new DeleteRequest(index, id);
        //执行客户端请求
        DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        log.info("索引为: {}, id为: {}删除数据成功", index, id);
    }


    /**
     * 通过ID更新数据
     */
    public void updateDataById(Object object, String index, String id) throws IOException {
        //更新请求
        UpdateRequest update = new UpdateRequest(index, id);

        //保证数据实时更新
        //update.setRefreshPolicy("wait_for");

        update.timeout("1s");
        update.doc(JSON.toJSONString(object), XContentType.JSON);
        //执行更新请求
        UpdateResponse update1 = restHighLevelClient.update(update, RequestOptions.DEFAULT);
        log.info("索引为: {}, id为: {}, 更新数据成功", index, id);
    }


    /**
     * 通过ID更新数据,保证实时性
     */
    public void updateDataByIdNoRealTime(Object object, String index, String id) throws IOException {
        //更新请求
        UpdateRequest update = new UpdateRequest(index, id);

        //保证数据实时更新
        update.setRefreshPolicy("wait_for");

        update.timeout("1s");
        update.doc(JSON.toJSONString(object), XContentType.JSON);
        //执行更新请求
        UpdateResponse update1 = restHighLevelClient.update(update, RequestOptions.DEFAULT);
        log.info("索引为: {}, id为: {}, 更新数据成功", index, id);
    }


    /**
     * 通过ID获取数据
     */
    public Map<String, Object> searchDataById(String index, String id, String fields) throws IOException {
        GetRequest request = new GetRequest(index, id);
        if (StringUtils.isNotEmpty(fields)) {
            //只查询特定字段。如果需要查询所有字段则不设置该项。
            request.fetchSourceContext(new FetchSourceContext(true, fields.split(","), Strings.EMPTY_ARRAY));
        }
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        Map<String, Object> map = response.getSource();
        //为返回的数据添加id
        map.put("id", response.getId());
        return map;
    }

    /**
     * 通过ID判断文档是否存在
     */
    public boolean existsById(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        //不获取返回的_source的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        return restHighLevelClient.exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 高亮结果集 特殊处理
     */
    public List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, HighlightField> high = hit.getHighlightFields();
            HighlightField title = high.get(highlightField);

            hit.getSourceAsMap().put("id", hit.getId());

            Map<String, Object> sourceAsMap = hit.getSourceAsMap();//原来的结果
            //解析高亮字段,将原来的字段换为高亮字段
            if (title != null) {
                Text[] texts = title.fragments();
                String nTitle = "";
                for (Text text : texts) {
                    nTitle += text;
                }
                //替换
                sourceAsMap.put(highlightField, nTitle);
            }
            list.add(sourceAsMap);
        }
        return list;
    }

    /**
     * 查询并分页
     */
    public List<Map<String, Object>> searchListData(String index,
                                                    SearchSourceBuilder query,
                                                    Integer size,
                                                    Integer from,
                                                    String fields,
                                                    String sortField,
                                                    String highlightField) throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder builder = query;
        if (StringUtils.isNotEmpty(fields)) {
            //只查询特定字段。如果需要查询所有字段则不设置该项。
            builder.fetchSource(new FetchSourceContext(true, fields.split(","), Strings.EMPTY_ARRAY));
        }
        from = from <= 0 ? 0 : from * size;
        //设置确定结果要从哪个索引开始搜索的from选项，默认为0
        builder.from(from);
        builder.size(size);
        if (StringUtils.isNotEmpty(sortField)) {
            //排序字段，注意如果proposal_no是text类型会默认带有keyword性质，需要拼接.keyword
            builder.sort(sortField + ".keyword", SortOrder.ASC);
        }
        //高亮
        HighlightBuilder highlight = new HighlightBuilder();
        highlight.field(highlightField);
        //关闭多个高亮
        highlight.requireFieldMatch(false);
        highlight.preTags("<span style='color:red'>");
        highlight.postTags("</span>");
        builder.highlighter(highlight);
        //不返回源数据。只有条数之类的数据。
        //builder.fetchSource(false);
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.error("==" + response.getHits().getTotalHits());
        if (response.status().getStatus() == 200) {
            // 解析对象
            return setSearchResponse(response, highlightField);
        }
        return null;
    }
}
