package com.togedog.member.entity;

import com.togedog.friend.entity.Friend;
import com.togedog.match.entity.Match;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "member")
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_nickname", nullable = false)
    private String nickName;

    @Column(name = "member_birth", nullable = false)
    private String birth;

    @Column(name = "member_email", nullable = false)
    private String email;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "member_profile_image")
    private String profileImage;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_gender", nullable = false)
    private memberGender gender;

    @Column(name = "member_report_cnt", nullable = false)
    private int reportCount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_status", nullable = false)
    private memberStatus status = Member.memberStatus.RESTRICTION;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "member")
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Friend> members = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Matching> matchings = new ArrayList<>();

    public void addMatching(Matching matching) {
        matchings.add(matching);
        if (matching.getHostMember() != this) {
            matching.addMember(this);
        }
    }

    public void addFriend(Friend friend) {
        this.friends.add(friend);
        if (friend.getMember() != this) {
            friend.setMember(this);
        }
    }

    public void addMember(Friend friend) {
        this.members.add(friend);
        if (friend.getMember() != this) {
            friend.setMember(this);
        }
    }

     @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public enum memberGender {
        M("남성"),
        F("여성");

        @Getter
        private String gender;

        memberGender(String gender) {
            this.gender = gender;
        }
    }

    public enum memberStatus {
        NO_RESTRICTION("활성 상태"),
        RESTRICTION("비활성 상태"),
        DELETED("탈퇴 상태");

        @Getter
        private String status;

        memberStatus(String status) {
            this.status = status;
        }
    }
}
