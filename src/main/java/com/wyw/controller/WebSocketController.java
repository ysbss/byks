package com.wyw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyw.pojo.SocketChat;
import com.wyw.service.SocketChatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Controller
@RequestMapping("/WebSocket")
public class WebSocketController {
    @Resource
    SocketChatService socketChatService;

    @RequestMapping("/fetchChatNews/{scReceiveId}/{pageNum}")
    public String fetchChatNews(Model model,
                                HttpSession session,
                                @PathVariable(value = "scReceiveId")Long scReceiveId,
                                @PathVariable(value = "pageNum",required = false) Integer pageNum){
        PageHelper.startPage(pageNum,3);
        List<SocketChat> queryChatNews = socketChatService.queryChatNews(scReceiveId);
        PageInfo<SocketChat> pageQueryChatNews=new PageInfo<SocketChat>(queryChatNews);
        model.addAttribute("queryChatNews",queryChatNews);
        model.addAttribute("pageQueryChatNews",pageQueryChatNews);
        return "chatNews";
    }

    @RequestMapping("/startChat/{scSendId}/{scReceiveId}")
    public String startChat(@PathVariable(value = "scSendId") Long scSendId,
                            @PathVariable(value = "scReceiveId") Long scReceiveId,
                            Model model){
        Map<String,Object> queryMap=new HashMap<String,Object>();
        queryMap.put("scSendId",scSendId);
        queryMap.put("scReceiveId",scReceiveId);
        model.addAttribute("receiveId",scSendId);
        model.addAttribute("resultChatList",socketChatService.querySocketChatList(queryMap));
        return "ChatRoom";
    }

    @ResponseBody
    @RequestMapping("/queryChatListByAjax")
    public List<SocketChat> queryChatListByAjax(Integer scSendId,Integer scReceiveId){
        Map<String,Object> queryMap=new HashMap<String,Object>();
        queryMap.put("scSendId",scSendId);
        queryMap.put("scReceiveId",scReceiveId);
        System.out.println("scSendId:"+scSendId+",scReceiveId:"+scReceiveId);
        return socketChatService.querySocketChatList(queryMap);
    }
}
