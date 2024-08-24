package com.togedog.chat.mapper;

import com.togedog.chat.dto.ChatDto;
import com.togedog.chat.entity.Chat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    Chat chatSendDtoToChat(ChatDto.Send chat);
    default ChatDto.Response chatToChatResponseDto(Chat chat){
        ChatDto.Response response = new ChatDto.Response();
        response.setContent(chat.getContent());
        response.setType(chat.getType());
        response.setSender(chat.getSender());
        response.setSendTime(chat.getSendTime());
        return response;
    }
}