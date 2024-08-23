package com.togedog.chatRoom.dto;

import com.togedog.chatRoom.entity.ChatRoomMember;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

import java.time.LocalDateTime;
import java.util.List;

public class ChatRoomDto {
    public static class Response {
        private String chatRoomName;
        private String chatDataPath;
        private LocalDateTime updatedAt;
        private List<ChatRoomMember> chatRoomMembers;
    }
}
