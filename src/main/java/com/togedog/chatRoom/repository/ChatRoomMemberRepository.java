package com.togedog.chatRoom.repository;

import com.togedog.chatRoom.entity.ChatRoom;
import com.togedog.chatRoom.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    Optional<ChatRoomMember> findByChatRoom(ChatRoom chatRoom);
}
