package com.wyw.service;

import com.wyw.pojo.SocketChat;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
public interface SocketChatService {
    /**
     * @param queryChatMap  查询条件
     * @return list
     * 查询聊天记录根据条件
     * */
    List<SocketChat> querySocketChatList(Map<String,Object> queryChatMap);

    /**
     * @param saveChatMap 保存的对象
     * @return int
     * 保存某条记录
     * */
    int saveSocketChat(Map<String,Object> saveChatMap);

    /**
     * @param saveChatMaps 保存的对象
     * @return int
     * 批量保存聊天记录
     * */
    int saveGroupSocketChat(List<Map<String,Object>> saveChatMaps);

    /**
     * @param updateChatMap 更新的对象
     * @return int
     * 更新聊天记录,主要用于将过期聊天记录标记为不可访问
     * */
    int updateSocketChat(Map<String,Object> updateChatMap);

    /**
     * @param deleteChatMap 删除的对象
     * @return int
     * 删除聊天记录
     * */
    int deleteSocketChat(Map<String,Object> deleteChatMap);

    /**
     * @param scReceiveId 要查询的所有的消息列表的id
     * @return list
     * 在消息列表显示有哪些人发来的消息
     * */
    List<SocketChat> queryChatNews(Long scReceiveId);

}
