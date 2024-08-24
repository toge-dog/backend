package com.togedog.comment.service;

import com.togedog.board.repository.BoardRepository;
import com.togedog.comment.entity.Comment;
import com.togedog.comment.repository.CommentRepository;
import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.member.entity.Member;
import com.togedog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public Comment createComment(Comment comment, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication);
        comment.setMember(member);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment, Authentication authentication) {
        Comment findComment = findVerifiedComment(comment.getCommentId());
        Optional.ofNullable(comment.getContent())
                .ifPresent(content -> comment.setContent(content));
        return commentRepository.save(findComment);
    }

    public void deleteComment(Comment comment, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication);
        comment.setMember(member);
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Comment findVerifiedComment(long commentId){
        Optional<Comment> findComment = commentRepository.findById(commentId);
        Comment resultComment = findComment.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return resultComment;
    }

    private Member extractMemberFromAuthentication(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        String email = (String) authentication.getPrincipal();
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
