package com.togedog.comment.controller;

import com.togedog.comment.dto.CommentDto;
import com.togedog.comment.entity.Comment;
import com.togedog.comment.mapper.CommentMapper;
import com.togedog.comment.service.CommentService;
import com.togedog.dto.SingleResponseDto;
import com.togedog.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class CommentController {
    private final static String COMMENT_DEF_URL = "/boards";
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/{board-id}/comments")
    public ResponseEntity<Void> postComment(@PathVariable("board-id") @Positive long boardId,
                                       @Valid @RequestBody CommentDto.Post requestBody,
                                      Authentication authentication) {
        requestBody.setBoardId(boardId);
        Comment comment = commentService.createComment(commentMapper.commentPostToComment(requestBody),authentication);
        URI location = UriCreator.createUri(COMMENT_DEF_URL, comment.getCommentId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{board-id}/comments/{comment-id}")
    public ResponseEntity<SingleResponseDto<CommentDto.Response>> patchComment(@PathVariable("board-id") @Positive long boardId,
                                                                                   @PathVariable("comment-id") @Positive long commentId,
                                       @Valid @RequestBody CommentDto.Patch requestBody,
                                       Authentication authentication){
        requestBody.setCommentId(commentId);
        Comment comment = commentService.updateComment(commentMapper.commentPatchToComment(requestBody),authentication);
        return new ResponseEntity<>(
                new SingleResponseDto<>(commentMapper.commentToCommentResponse(comment)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}/comments/{comment-id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("board-id") @Positive long boardId,
                                        @PathVariable("comment-id") @Positive long commentId,
                                        Authentication authentication){
        Comment comment = commentService.findVerifiedComment(commentId);
        commentService.deleteComment(comment,authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}