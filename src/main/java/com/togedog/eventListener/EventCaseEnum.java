package com.togedog.eventListener;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EventCaseEnum {
    @Getter
    @AllArgsConstructor
    public enum EventCase{
        DELETE_RELATED_MATCHING_STAND_BY_DATA(1,"연관된 MatchingStandBy 데이터 삭제"),
        SUCCESS_RELATED_MATCHING_DATA(2,"연관된 Matching 데이터 성공으로 상태 변경");

        private int statusNumber;

        private String statusDescription;
    }
}
