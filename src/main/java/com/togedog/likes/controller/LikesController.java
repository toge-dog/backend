package com.togedog.likes.controller;

import com.togedog.likes.dto.LikesDto;
import com.togedog.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<Void> toggleLike(@RequestBody LikesDto.Post requestBody){
        likesService.toggleLikes(requestBody.getMemberId(), requestBody.getBoardId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
