package com.togedog.chat.controller;

import com.sun.istack.NotNull;
import com.togedog.chat.entity.ChatMessage;
import com.togedog.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;

    @MessageMapping("/{chatRoomId}/Messages")
    public void sendMessage(@Payload @Valid ChatMessage chatMessage, @DestinationVariable String chatRoomId) {
        chatMessage = chatService.MessageSendAndSave(chatMessage,chatRoomId);
        template.convertAndSend("/topic/chat/" + chatRoomId, chatMessage);
    }

    @MessageMapping("/{chatRoomId}/chat.addUser")
    public void addUser(@DestinationVariable String chatRoomId, @Payload @NotNull ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

        //채팅방 인원 추가
//        chatRoomService.enter(chatRoomId);

        // 구독한 채팅방에 입장 메시지 보내기
        headerAccessor.getSessionAttributes().put("chatRoomId", chatRoomId);
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        template.convertAndSend("/topic/chat/" + chatRoomId, chatMessage);
    }
}