package com.tmdrk.chat.elasticsearch.service;

import com.tmdrk.chat.common.entity.MessageInfo;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName IChatService
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/18 14:58
 * @Version 1.0
 **/
public interface IChatService {
    boolean createChatIndex() throws IOException;
    boolean deleteChatIndex(String idx) throws IOException;
    boolean insertDoc(MessageInfo messageInfo) throws IOException;
    Map<String, Object> searchdoc(String id)throws IOException;

    boolean updateDoc(MessageInfo messageInfo) throws IOException;

    boolean docDelete(String id)throws IOException;

    boolean batchDoc() throws IOException;

    String test();
}
