package com.codeying.component.chat;

import com.codeying.component.ApiResult;
import com.codeying.component.utils.CommonUtils;
import com.codeying.controller.BaseController;
import com.codeying.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
public class ChatController extends BaseController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    @ResponseBody
    public ApiResult chat() {
        //根据联系对方用户类型rtype获取用户姓名
        LoginUser loginUser = getCurrentUser();
        List<Chat> chatList = chatService.chatList(loginUser.getId(), loginUser.getRole());
        return successData(chatList);
    }

    @PostMapping
    @ResponseBody
    public ApiResult receiveAndSend(@RequestBody Map<String,String> map) {
        String rid = map.get("rid");
        String rtype = map.get("rtype");
        String action = map.get("action");
        String content = map.get("content");
        if ("send".equals(action)) {
            return send(rid, rtype, content);
        } else if ("receive".equals(action)) {
            return receive(rid, rtype);
        } else if ("unread".equals(action)) {
            LoginUser loginUser = getCurrentUser();
            int count = chatService.unreadCount(loginUser.getId(), null);
            return successData(count);
        }
        return success();
    }

    public ApiResult receive(String rid, String rtype) {
        LoginUser loginUser = getCurrentUser();
        if (loginUser == null) {
            return ApiResult.fail("尚未登陆！");
        }
        if (loginUser.getId().equals(rid)) {
            return ApiResult.fail("不可和自己聊天！");
        }
        List<Chat> chats = chatService.getChat(rid, rtype, loginUser.getId(), loginUser.getRole());
        return ApiResult.successData(chats);
    }

    public ApiResult send(String rid, String rtype, String content) {
        LoginUser loginUser = getCurrentUser();
        if (loginUser == null) {
            return ApiResult.fail("尚未登陆！");
        }
        Chat chat = new Chat();
        chat.setId(CommonUtils.newId());
        chat.setRid(rid);
        chat.setRtype(rtype);
        chat.setSid(loginUser.getId());
        chat.setStype(loginUser.getRole());
        chat.setContent(content);
        chat.setCreatetime(new Date());
        chat.setFlag("N");
        chatService.save(chat);
        return ApiResult.success();
    }
}
