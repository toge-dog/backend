package com.togedog.friend.controller;

import com.togedog.dto.MultiResponseDto;
import com.togedog.friend.dto.Dto;
import com.togedog.friend.entity.Friend;
import com.togedog.friend.mapper.FriendMapper;
import com.togedog.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final FriendMapper friendMapper;

    @GetMapping("/{friend-email}/{member-email}")
    public ResponseEntity getFriend(@PathVariable("friend-email") String friendEmail,
                                    @PathVariable("member-email") String memberEmail) {
        Friend friend = friendService.getFriend(friendEmail, memberEmail);
        Dto.Response response = friendMapper.friendToResponse(friend);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getFriends(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size) {
        Page<Friend> friendPage = friendService.getFriends(page -1, size);
        List<Friend> friendList = friendPage.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(
                    friendMapper.friendsToResponse(friendList), friendPage), HttpStatus.OK);
    }
}
