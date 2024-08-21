package com.togedog.board.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;

    public Board createBoard(Board board){
        Board savedBoard = boardRepository.save(board);
        return savedBoard;
    }

    public Board patchBoard(Board board){
        Optional<Board> findBoard =
                boardRepository.findById(board.getBoardId());
        Optional.ofNullable(board.getBoardType())
                .ifPresent(boardType -> board.setBoardType(boardType));
        Optional.ofNullable(board.getContent())
                .ifPresent(content -> board.setContent(content));
        Optional.ofNullable(board.getTitle())
                .ifPresent(title -> board.setTitle(title));
        Optional.ofNullable(board.getContentImg())
                .ifPresent(contentImg -> board.setContentImg(contentImg));
        return boardRepository.save(board);
    }

    public Board getBoard(Board board){
        if (board.getBoardStatus() == Board.BoardStatus.BOARD_DELETED){
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND);
        }
        return board;
    }

    public void deleteBoard(long boardId){
        Board findBoard = findVerifiedBoard(boardId);
        findBoard.setBoardStatus(Board.BoardStatus.BOARD_DELETED);
    }

    //특정 id가진 게시물이 데이터베이스에 존재하는지 확인, 존재하지않으면 예외 발생
    @Transactional(readOnly = true)
    public Board findVerifiedBoard(long boardId){
        Optional<Board> findBoard =
                boardRepository.findById(boardId);
        Board result = findBoard.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return result;
    }

    public Page<Board> findBoards(int page, int size){
        return boardRepository.findAll(PageRequest.of(page,size, Sort.by("boardId").descending()));
    }

    public void toggleLikes(Likes likes){
        Board board = findVerifiedBoard(likes.getBoard().getBoardId());
        Member member = memberRepository.findById(likes.getMember().getMemberId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        if (likesRepository.findByMember(member).isPresent()) {
            likesRepository.delete(likes);
            board.setLikesCount(board.getLikesCount()-1);
        } else {
            likesRepository.save(likes);
            board.setLikesCount(board.getLikesCount()+1);
        }
        boardRepository.save(board);
    }

}
