package com.codeying.component.chat;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 */
public interface ChatService extends IService<Chat> {

    String getUserName(String id, String utype);

    List<Chat> chatList(String id, String role);

    List<Chat> getChat(String rid, String rtype, String id, String role);

    int unreadCount(String userId, String hisId);
}

