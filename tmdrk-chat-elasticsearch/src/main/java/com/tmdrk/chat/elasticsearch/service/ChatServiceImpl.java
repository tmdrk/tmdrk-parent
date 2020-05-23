package com.tmdrk.chat.elasticsearch.service;

import com.tmdrk.chat.common.entity.MessageInfo;
import com.tmdrk.chat.common.utils.DateUtils;
import com.tmdrk.chat.common.utils.StringUtil;
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
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName ChatServiceImpl
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/18 14:58
 * @Version 1.0
 **/
@Service("chatServiceImpl")
public class ChatServiceImpl implements IChatService{
    Logger logger = LoggerFactory.getLogger(getClass());
    static Random random = new Random();

    @Autowired
    RestHighLevelClient client;

    /**
     * @Author zhoujie
     * @Description 创建索引库
     * @Date 17:34 2019/7/18
     * @Param []
     * @return boolean
     **/
    @Override
    public boolean createChatIndex() throws IOException {
        //创建索引请求对象，并设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("tmdrk_chat_1");
        //设置索引参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards",2)
                .put("number_of_replicas",1));

        //设置映射
        createIndexRequest.mapping("doc","{\n" +
                "\t\"properties\": {\n" +
                "\t\t\"from\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"to\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"content\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"msgType\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"timestamp\": {\n" +
                "\t\t\t\"type\": \"date\",\n" +
                "\t\t\t\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", XContentType.JSON);

        createIndexRequest.alias(new Alias("t_chat"));
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
    }

    /**
     * @Author zhoujie
     * @Description 删除索引库
     * @Date 17:34 2019/7/18
     * @Param []
     * @return boolean
     **/
    @Override
    public boolean deleteChatIndex(String idx) throws IOException {
        //删除索引请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(idx);
        //删除索引
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest);
        //删除索引响应结果
        boolean acknowledged = deleteIndexResponse.isAcknowledged();
        return acknowledged;
    }

    @Override
    public boolean insertDoc(MessageInfo messageInfo) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("from", messageInfo.getFrom());
        jsonMap.put("to", messageInfo.getTo());
        jsonMap.put("toName", messageInfo.getToName());
        jsonMap.put("content", messageInfo.getContent());
        jsonMap.put("type", messageInfo.getType());
        jsonMap.put("timestamp", DateUtils.getNowTime());
        //索引请求对象
        IndexRequest indexRequest = new IndexRequest("t_chat","doc",messageInfo.getId().toString());
        //指定索引文档内容
        indexRequest.source(jsonMap);
        //索引响应对象
        IndexResponse indexResponse = client.index(indexRequest);
        //获取响应结果
        DocWriteResponse.Result result = indexResponse.getResult();
        if(result==DocWriteResponse.Result.CREATED){
            logger.info("新增文档成功");
        }else if(result==DocWriteResponse.Result.UPDATED){
            logger.info("更新文档成功");
        }
        System.out.println(result);
        return true;
    }

    @Override
    public Map<String, Object> searchdoc(String id) throws IOException{
        GetRequest getRequest = new GetRequest(
                "t_chat",
                "doc",
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

    @Override
    public boolean updateDoc(MessageInfo messageInfo) throws IOException{
        Map<String, Object> jsonMap = new HashMap<>();
        if(messageInfo.getFrom()!=null){
            jsonMap.put("from", messageInfo.getFrom());
        }
        if(messageInfo.getTo()!=null){
            jsonMap.put("to", messageInfo.getTo());
        }
        if(messageInfo.getToName()!=null){
            jsonMap.put("toName", messageInfo.getToName());
        }
        if(messageInfo.getContent()!=null){
            jsonMap.put("content", messageInfo.getContent());
        }
        if(messageInfo.getType()!=null){
            jsonMap.put("type", messageInfo.getType());
        }
        jsonMap.put("timestamp", DateUtils.getNowTime());
        //索引请求对象
        UpdateRequest updateRequest = new UpdateRequest("t_chat","doc",messageInfo.getId().toString());
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

    @Override
    public boolean docDelete(String id) throws IOException {
        //删除索引请求对象
        DeleteRequest deleteRequest = new DeleteRequest("t_chat","doc",id);
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

    @Override
    public boolean batchDoc() throws IOException {
        for (int i=0;i<10000000;i++){
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("user_id", i+1);
            jsonMap.put("user_name", StringUtil.randomName());
            jsonMap.put("location", randomLocation(35,110,1,5));
            //索引请求对象
            IndexRequest indexRequest = new IndexRequest("tmdrk_location","doc",(i+1)+"");
            //指定索引文档内容
            indexRequest.source(jsonMap);
            //索引响应对象
            IndexResponse indexResponse = client.index(indexRequest);
            //获取响应结果
            DocWriteResponse.Result result = indexResponse.getResult();
            if(result==DocWriteResponse.Result.CREATED){
                if(i%100==0){
                    logger.info("新增文档成功:"+i);
                }
            }else if(result==DocWriteResponse.Result.UPDATED){
                if(i%100==0) {
                    logger.info("更新文档成功:"+i);
                }
            }
        }
        return true;
    }

    @Override
    public String test() {
        return "你好看山东福克斯";
    }


    public static float randomFloat(float start,int range,int scale){
        Double base = new Double(Math.pow(10,scale));
        int tem = random.nextInt(range*base.intValue());
        return start+tem/base.floatValue();
    }

    public static String randomLocation(float longitude,float latitude,int range,int scale){
       return randomFloat(longitude,range,scale)+","+randomFloat(latitude,range,scale);
    }

    public static void main(String[] args) {
        //110-111 35-36
        for(int i=0;i<100;i++){
            System.out.println(randomLocation(110,35,1,5));
        }
    }
}
