package com.togedog.chat.service;

import com.togedog.chat.entity.ChatMessage;
import com.togedog.chatRoom.entity.ChatRoom;
import com.togedog.chatRoom.repository.ChatRoomMemberRepository;
import com.togedog.chatRoom.repository.ChatRoomRepository;
import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.member.entity.Member;
import com.togedog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;

    public ChatMessage MessageSendAndSave(ChatMessage chatMessage, String chatRoomId) {
        Member member = memberRepository.findByEmail(chatMessage.getName())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        chatMessage.setName(member.getNickName());
        return chatMessage;
    }
}
