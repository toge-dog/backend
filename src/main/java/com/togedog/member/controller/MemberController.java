package com.togedog.member.controller;

import com.togedog.dto.MultiResponseDto;
import com.togedog.dto.SingleResponseDto;
import com.togedog.friend.service.FriendService;
import com.togedog.member.dto.MemberDto;
import com.togedog.member.mapper.MemberMapper;
import com.togedog.member.entity.Member;
import com.togedog.member.service.MemberService;
import com.togedog.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/members")
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/members";
    private final MemberService service;
    private final FriendService friendService;
    private final MemberMapper mapper;

    public MemberController(MemberService service, FriendService friendService, MemberMapper mapper) {
        this.service = service;
        this.friendService = friendService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post post) {
        Member member = service.createMember(mapper.memberPostToMember(post));
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, member.getMemberId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Member member = service.findMember(memberId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToResponseDto(member)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size) {
        Page<Member> pageMembers = service.findMembers(page -1, size);
        List<Member> members = pageMembers.getContent();

        return new ResponseEntity(
                new MultiResponseDto<>(mapper.membersToResponseDto(members), pageMembers), HttpStatus.OK);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch patch) {
        patch.setMemberId(memberId);
        Member member = service.updateMember(mapper.memberPatchToMember(patch));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToResponseDto(member)), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {
        service.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{member-email}/friends/{friend-email}")
    public ResponseEntity addFriend(@PathVariable("member-email") String memberEmail, @PathVariable("friend-email") String friendEmail) {
        friendService.addFriend(memberEmail, friendEmail);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/accept/{member-email}/{friend-email}")
    public ResponseEntity acceptFriendRequest(@PathVariable("member-email") String memberEmail,
                                              @PathVariable("friend-email") String friendEmail) {
        friendService.acceptFriendRequest(memberEmail, friendEmail);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/reject/{member-email}/{friend-email}")
    public ResponseEntity rejectFriendRequest(@PathVariable("member-email") String memberEmail,
                                              @PathVariable("friend-email") String friendEmail) {
        friendService.rejectFriendRequest(memberEmail, friendEmail);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
