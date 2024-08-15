package com.togedog.matching.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.togedog.audit.Auditable;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Matching extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchingId;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column
    @Enumerated(value = EnumType.STRING)
    private MatchingStatus matchingStatus = MatchingStatus.MATCHING_HOSTING;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Long hostMemberId;

    @AllArgsConstructor
    public enum MatchingStatus{
        MATCHING_HOSTING(1,"매칭 호스팅 중"),
        MATCHING_CANCEL(2,"매칭 취소"),
        MATCHING_SUCCESS(3,"매칭 성공");

        @Getter
        private int statusNumber;

        @Getter
        private String statusDescription;
    }
}
