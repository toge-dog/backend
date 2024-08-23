package com.togedog.chatRoom.mapper;

import com.togedog.chatRoom.dto.ChatRoomDto;
import com.togedog.chatRoom.entity.ChatRoom;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    ChatRoomDto.Response chatRoomToChatRoomResponseDto(ChatRoom chatRoom);
    List<ChatRoomDto.Response> chatRoomsToChatRoomResponseDto(List<ChatRoom> chatRooms);
}
