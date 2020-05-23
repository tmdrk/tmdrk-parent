package com.tmdrk.chat.elasticsearch.service;

import com.tmdrk.chat.common.entity.es.EsIndex;
import com.tmdrk.chat.common.entity.es.Settings;
import com.tmdrk.chat.common.utils.ElasticsearchUtil;
import com.tmdrk.chat.common.utils.StringUtil;
import org.apache.commons.beanutils.BeanMap;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @ClassName ElasticSearchServiceImpl
 * @Description
 * @Author zhoujie
 * @Date 2019/12/18 15:02
 * @Version 1.0
 **/
@Service("elasticSearchServiceImpl")
public class ElasticSearchServiceImpl<T>{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RestHighLevelClient client;

    public boolean createIndex(String indexName,String type,Class clazz) {
        try {
            if(StringUtil.isBlank(indexName,clazz)){
                logger.error("参数不能为空");
                return false;
            }
            Field[] fields = clazz.getDeclaredFields();
            if (!clazz.isAnnotationPresent(EsIndex.class)) {
                logger.error("EsIndex annotation not exist");
            }
            EsIndex indexAnno = (EsIndex)clazz.getAnnotation(EsIndex.class);
            Settings settings = indexAnno.settings();
            int numberOfShards = settings.numberOfShards();
            int numberOfReplicas = settings.numberOfReplicas();

            String[] aliases = indexAnno.aliases();

            if(!indexAnno.needMapping()){
                System.out.println("mapping no need");
            }
            String methodJson = "{\"properties\":{";
            methodJson += ElasticsearchUtil.dealWithFields(fields);
            methodJson += "}}";
            logger.info("methodJson："+methodJson);

            //创建索引请求对象，并设置索引名称
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
            //设置索引参数
            createIndexRequest.settings(org.elasticsearch.common.settings.Settings.builder().
                    put("number_of_shards",numberOfShards).put("number_of_replicas",numberOfReplicas));

            //设置映射
            createIndexRequest.mapping(type==null?"doc":type,methodJson, XContentType.JSON);

            //设置别名
            for(String aliase:aliases){
                createIndexRequest.alias(new Alias(aliase));
            }
            //创建索引操作客户端
            IndicesClient indices = client.indices();

            //创建响应对象
            CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);
            //得到响应结果
            boolean acknowledged = createIndexResponse.isAcknowledged();
            boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
            logger.info("acknowledged =" + acknowledged);
            logger.info("shardsAcknowledged =" + shardsAcknowledged);
            return acknowledged;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteIndex(String indexName) throws IOException {
        if(StringUtil.isBlank(indexName)){
            logger.error("参数不能为空");
            return false;
        }
        //删除索引请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        //删除索引
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest);
        //删除索引响应结果
        boolean acknowledged = deleteIndexResponse.isAcknowledged();
        return acknowledged;
    }

    public int insertOrUpdate(String indexName,String type,String id,T obj) throws IOException {
        if(StringUtil.isBlank(indexName,id,obj)){
            logger.error("参数不能为空");
            return 0;
        }
//        Field[] declaredFields = obj.getClass().getDeclaredFields();
//        Map<String, Object> jsonMap = new HashMap<>();
//        for(Field field:declaredFields){
//            try {
//                jsonMap.put(field.getName(),field.get(obj));
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
        BeanMap jsonMap = new BeanMap(obj);
        //索引请求对象
        IndexRequest indexRequest = new IndexRequest(indexName,type==null?"doc":type,id);
        //指定索引文档内容
        indexRequest.source(jsonMap);
        //索引响应对象
        IndexResponse indexResponse = client.index(indexRequest);
        //获取响应结果
        DocWriteResponse.Result result = indexResponse.getResult();
        if(result==DocWriteResponse.Result.CREATED){
            logger.info("新增文档成功");
            return 1;
        }else if(result==DocWriteResponse.Result.UPDATED){
            logger.info("更新文档成功");
            return 2;
        }
        return 3;
    }

    public boolean delete(String indexName,String type,String id) throws IOException {
        if(StringUtil.isBlank(indexName,id)){
            logger.error("参数不能为空");
            return false;
        }
        //删除索引请求对象
        DeleteRequest deleteRequest = new DeleteRequest(indexName,type==null?"doc":type,id);
        //响应对象
        DeleteResponse deleteResponse = client.delete(deleteRequest);
        //获取响应结果
        DocWriteResponse.Result result = deleteResponse.getResult();
        if(result==DocWriteResponse.Result.DELETED){
            logger.info("删除文档成功");
            return true;
        }
        return false;
    }

    public boolean update(String indexName,String type,String id,T obj) throws IOException {
        if(StringUtil.isBlank(indexName,id,obj)){
            logger.error("参数不能为空");
            return false;
        }
//        Field[] declaredFields = obj.getClass().getDeclaredFields();
//        Map<String, Object> jsonMap = new HashMap<>();
//        for(Field field:declaredFields){
//            try {
//                if(field.get(obj)!=null){
//                    jsonMap.put(field.getName(),field.get(obj));
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
        BeanMap jsonMap = new BeanMap(obj);
        //索引请求对象
        UpdateRequest updateRequest = new UpdateRequest(indexName,type==null?"doc":type,id);
        //指定索引文档内容
        updateRequest.doc(jsonMap);
        //索引响应对象
        UpdateResponse updateResponse = client.update(updateRequest);
        //获取响应结果
        DocWriteResponse.Result result = updateResponse.getResult();
        if(result==DocWriteResponse.Result.UPDATED){
            logger.info("更新文档成功");
            return true;
        }
        return false;
    }

    /********************** 搜索 start *********************/
    public Map<String, Object> searchById(String indexName,String type,String id) throws IOException{
        if(StringUtil.isBlank(indexName,id)){
            logger.error("参数不能为空");
            return null;
        }
        GetRequest getRequest = new GetRequest(
                indexName,
                type==null?"doc":type,
                id);
        GetResponse getResponse = client.get(getRequest);
        boolean exists = getResponse.isExists();
        if(exists){
            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
            return sourceAsMap;
        }else{
            return null;
        }
    }

    /********************** 搜索 end *********************/
}
