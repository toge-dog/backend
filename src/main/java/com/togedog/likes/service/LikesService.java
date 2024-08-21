package com.togedog.likes.service;

import com.togedog.board.entity.Board;
import com.togedog.board.repository.BoardRepository;
import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
import com.togedog.likes.dto.LikesDto;
import com.togedog.likes.entity.Likes;
import com.togedog.likes.repository.LikesRepository;
import com.togedog.member.entity.Member;
import com.togedog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void toggleLikes(Long memberId, Long boardId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));

        Optional<Likes> existingLikes = likesRepository.findByMemberAndBoard(member, board);
        if (existingLikes.isPresent()){
            likesRepository.delete(existingLikes.get());
            board.setLikesCnt(board.getLikesCnt()-1);
        } else {
            Likes likes = new Likes();
            likes.setMember(member);
            likes.setBoard(board);
            likesRepository.save(likes);
            board.setLikesCnt(board.getLikesCnt()+1);
        }

        boardRepository.save(board);
    }
}
