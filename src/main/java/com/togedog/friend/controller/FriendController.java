package com.togedog.friend.controller;

import com.togedog.friend.dto.Dto;
import com.togedog.friend.entity.Friend;
import com.togedog.friend.mapper.FriendMapper;
import com.togedog.friend.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;
    private final FriendMapper friendMapper;

    public FriendController(FriendService friendService, FriendMapper friendMapper) {
        this.friendService = friendService;
        this.friendMapper = friendMapper;
    }

    @GetMapping("/{friend-email}/{member-email}")
    public ResponseEntity getFriend(@PathVariable("friend-email") String friendEmail,
                                    @PathVariable("member-email") String memberEmail) {
        Friend friend = friendService.getFriend(friendEmail, memberEmail);
        Dto.Response response = friendMapper.friendToResponse(friend);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
