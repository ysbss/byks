package com.wyw.service;

import com.wyw.dao.SocketChatMapper;
import com.wyw.pojo.SocketChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("SocketChatServiceImpl")
public class SocketChatServiceImpl implements SocketChatService{

    @Autowired
    SocketChatMapper socketChatMapper;

    @Override
    public List<SocketChat> querySocketChatList(Map<String, Object> queryChatMap) {
        return socketChatMapper.querySocketChatList(queryChatMap);
    }

    @Override
    public int saveSocketChat(Map<String, Object> saveChatMap) {
        return socketChatMapper.saveSocketChat(saveChatMap);
    }

    @Override
    public int saveGroupSocketChat(List<Map<String, Object>> saveChatMaps) {
        return socketChatMapper.saveGroupSocketChat(saveChatMaps);
    }

    @Override
    public int updateSocketChat(Map<String, Object> updateChatMap) {
        return socketChatMapper.updateSocketChat(updateChatMap);
    }

    @Override
    public int deleteSocketChat(Map<String, Object> deleteChatMap) {
        return socketChatMapper.deleteSocketChat(deleteChatMap);
    }

    @Override
    public List<SocketChat> queryChatNews(Long scReceiveId) {
        return socketChatMapper.queryChatNews(scReceiveId);
    }
}
