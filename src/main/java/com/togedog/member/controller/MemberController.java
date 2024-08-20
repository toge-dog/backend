package com.togedog.member.controller;

import com.togedog.auth.jwt.JwtTokenizer;
import com.togedog.dto.MultiResponseDto;
import com.togedog.dto.SingleResponseDto;
import com.togedog.friend.service.FriendService;
import com.togedog.member.dto.MemberDto;
import com.togedog.member.mapper.MemberMapper;
import com.togedog.member.entity.Member;
import com.togedog.member.service.MemberService;
import com.togedog.pet.entity.Pet;
import com.togedog.pet.mapper.PetMapper;
import com.togedog.pet.service.PetService;
import com.togedog.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping
@RequiredArgsConstructor
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/members";
    private final MemberService service;
    private final FriendService friendService;
    private final MemberMapper memberMapper;

    @PostMapping("/sign-up/members")
    public ResponseEntity signUpMember(@Valid @RequestBody MemberDto.Post post) {
        Member member = service.createMember(memberMapper.memberPostToMember(post));
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, member.getMemberId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("member")
    public ResponseEntity getMember(Authentication authentication) {
        Member member = service.findMember(authentication);

        return new ResponseEntity<>(
                new SingleResponseDto<>(memberMapper.memberToResponseDto(member)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size,
                                     Authentication authentication) {
        Page<Member> pageMembers = service.findMembers(page -1, size, authentication);
        List<Member> members = pageMembers.getContent();

        return new ResponseEntity(
                new MultiResponseDto<>(memberMapper.membersToResponseDto(members), pageMembers), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity patchMember(Authentication authentication,
                                      @Valid @RequestBody MemberDto.Patch patch) {
        Member member = service.updateMember(memberMapper.memberPatchToMember(patch),authentication);

        return new ResponseEntity<>(
                new SingleResponseDto<>(memberMapper.memberToResponseDto(member)), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteMember(Authentication authentication) {
        service.deleteMember(authentication);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/friends/{friend-email}")
    public ResponseEntity addFriend(@PathVariable("friend-email") String toEmail,
                                    Authentication authentication) {
        String fromEmail = authentication.getName();

        friendService.addFriend(fromEmail, toEmail);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/accept/{friend-id}")
    public ResponseEntity acceptFriendRequest(@PathVariable("friend-id") @Positive long friendId,
                                              Authentication authentication) {

        friendService.acceptFriendRequest(friendId, authentication);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/reject/{member-email}/{friend-email}")
    public ResponseEntity rejectFriendRequest(@PathVariable("member-email") String memberEmail,
                                              @PathVariable("friend-email") String friendEmail) {
        friendService.rejectFriendRequest(memberEmail, friendEmail);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
