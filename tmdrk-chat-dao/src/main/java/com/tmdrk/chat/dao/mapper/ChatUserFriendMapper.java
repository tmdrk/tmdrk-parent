package com.tmdrk.chat.dao.mapper;

import com.tmdrk.chat.dao.entity.ChatUserFriend;
import com.tmdrk.chat.dao.entity.ChatUserFriendExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatUserFriendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    int countByExample(ChatUserFriendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    int deleteByExample(ChatUserFriendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    int insert(ChatUserFriend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    int insertSelective(ChatUserFriend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    List<ChatUserFriend> selectByExample(ChatUserFriendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    ChatUserFriend selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    int updateByExampleSelective(@Param("record") ChatUserFriend record, @Param("example") ChatUserFriendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    int updateByExample(@Param("record") ChatUserFriend record, @Param("example") ChatUserFriendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    int updateByPrimaryKeySelective(ChatUserFriend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CHAT_USER_FRIEND
     *
     * @mbggenerated Wed May 13 00:49:04 GMT+08:00 2020
     */
    int updateByPrimaryKey(ChatUserFriend record);
}