package com.togedog.likes.controller;

import com.togedog.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boardId/likes")
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

//    @PostMapping
//    public ResponseEntity<Likes>
}
