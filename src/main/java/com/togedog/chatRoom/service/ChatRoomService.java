package com.togedog.chatRoom.service;

import com.togedog.chatRoom.entity.ChatRoom;
import com.togedog.chatRoom.repository.ChatRoomRepository;
import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.matching.entity.Matching;
import com.togedog.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository repository;

    public void createChatRoom(List<Member> members) {
        ChatRoom chatRoom = new ChatRoom();
        if(members.size()!=2)
            throw new BusinessLogicException(ExceptionCode.MATCH_NOT_FOUND);
        Member member1 = members.get(0);
        Member member2 = members.get(1);
        String chatRoomName = member1.getName() + "-" + member2.getName();
        chatRoom.addMember(member1);
        chatRoom.addMember(member2);
        chatRoom.setChatRoomName(chatRoomName);
        repository.save(chatRoom);
    }

}
