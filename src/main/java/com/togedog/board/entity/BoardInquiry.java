package com.togedog.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("I")
public class BoardInquiry extends Board {

    @Enumerated(value = EnumType.STRING)
    private BoardInquiryStatus boardInquiryStatus = BoardInquiryStatus.RECEIVED;

    @AllArgsConstructor
    public enum BoardInquiryStatus {
        RECEIVED("신고&문의 수신 완료"),
        COMPLETED("신고&문의 처리 완료");

        @Getter
        private String statusDescription;
    }
}
