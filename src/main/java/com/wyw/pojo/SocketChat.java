package com.wyw.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 鱼酥不是叔
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class SocketChat {
    /**
     * 主键id,使用java生成uuid
     * */
    private String scId;
    /**
     * 发送人id
     */
    private Long scSendId;
    /**
     *接收人id
     */
    private Long scReceiveId;
    /**
     * 发送的信息
     * */
    private String scChatMsg;
    /**
     * 消息发送时间
     * */
    private String scMsgSendTime;
    /**
     * 消息过期标志
     * */
    private Integer scFlag;
}
