package com.togedog.chat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChatMessage {
    @NotNull(message = "메시지 타입은 필수 값입니다.")
    private MessageType type;

    @NotBlank(message = "내용은 필수 값입니다.")
    private String content;

    @NotBlank(message = "송신자는 필수 값입니다.")
    private String sender;

    @NotBlank(message = "사용자 정보는 필수 값입니다.")
    private String name;

    public enum MessageType {
        CHAT, JOIN, LEAVE, ERROR,
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}