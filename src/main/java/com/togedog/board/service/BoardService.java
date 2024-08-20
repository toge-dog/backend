package com.togedog.board.service;

import com.togedog.board.entity.Board;
import com.togedog.board.repository.BoardRepository;
import com.togedog.exception.BusinessLogicException;
import com.togedog.exception.ExceptionCode;
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

    @PersistenceContext
    private EntityManager entityManager;

    public Board createBoard(Board board){
        Board savedBoard = boardRepository.save(board);
        return savedBoard;
    }

    public Board patchBoard(Board board){
        Optional<Board> findBoard =
                boardRepository.findById(board.getBoardId());
//        Optional.ofNullable(board.getBoardType())
//                .ifPresent(boardType -> board.setBoardType(boardType));
        Optional.ofNullable(board.getContent())
                .ifPresent(content -> board.setContent(content));
        Optional.ofNullable(board.getTitle())
                .ifPresent(title -> board.setTitle(title));
        Optional.ofNullable(board.getContentImg())
                .ifPresent(contentImg -> board.setContentImg(contentImg));
        return boardRepository.save(board);
    }

    public Board getBoard(Board board){
        return boardRepository.save(board);
    }

    public void deleteBoard(long boardId){
        Board findBoard = findVerifiedBoard(boardId);
    }

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


}
