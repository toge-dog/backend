package com.togedog.board.mapper;

import com.togedog.board.dto.BoardDto;
import com.togedog.board.dto.BoardDto.ResponseBoard;
import com.togedog.board.entity.BoardBoast;
import com.togedog.board.entity.BoardInquiry;
import com.togedog.board.entity.BoardReview;
import com.togedog.board.entity.BoardType;
import com.togedog.board.entity.BoardAnnouncement;
import com.togedog.board.entity.Board;
import com.togedog.comment.dto.CommentDto;
import com.togedog.comment.dto.CommentDto.Response;
import com.togedog.comment.entity.Comment;

import com.togedog.member.entity.Member;
import org.mapstruct.Mapper;


import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface BoardMapper {
    default Board boardDtoPostToBoard(BoardDto.Post requestBody){
        Member member = new Member();
        Board board;
        if(requestBody.getBoardType() == BoardType.REVIEW) {
            board = new BoardReview();
        } else if (requestBody.getBoardType() == BoardType.BOAST) {
            board = new BoardBoast();
        } else if (requestBody.getBoardType() == BoardType.INQUIRY) {
            board = new BoardInquiry();
        }else if (requestBody.getBoardType() == BoardType.ANNOUNCEMENT){
            board = new BoardAnnouncement();
        }else {
            board = new Board();
        }
        board.setTitle(requestBody.getTitle());
        board.setContent(requestBody.getContent());
        board.setContentImg(requestBody.getContentImg());
        board.setBoardType(requestBody.getBoardType());
        board.setMember(member);
        return board;
    }

    Board boardDtoPatchToBoard(BoardDto.Patch requestBody);

    default BoardDto.ResponseBoard boardToBoardDtoResponse(Board board) {
        BoardDto.ResponseBoard responseBoard = new BoardDto.ResponseBoard();

        responseBoard.setTitle(board.getTitle());
        responseBoard.setContent(board.getContent());
        responseBoard.setContentImg(board.getContentImg());
        responseBoard.setBoardType(board.getBoardType().getBoardDescription());
        responseBoard.setBoardStatus(board.getBoardStatus().getStatusDescription());
        responseBoard.setLikesCount(board.getLikesCount());
        responseBoard.setViewCount(board.getViewCount());

        List<CommentDto.Response> commentList = board.getComments().stream()
                .map(c -> {
                    CommentDto.Response commentResponseDto = new CommentDto.Response();
                    commentResponseDto.setCommentId(c);  // <----- 이부분
                    commentResponseDto.setComment(c);
                    return commentResponseDto;
                })
                .collect(Collectors.toList());

        // 댓글 리스트를 responseBoard에 설정
        responseBoard.setComments(commentList);

        return responseBoard;
    }

    default List<BoardDto.Response> boardToBoardDtoResponses(List<Board> boards) {
        return boards
                .stream()
                .filter(status -> !status.getBoardStatus().equals(Board.BoardStatus.BOARD_DELETED))
                .map(board -> BoardDto.Response
                        .builder()
                        .title(board.getTitle())
                        .content(board.getContent())
                        .boardType(board.getBoardType().getBoardDescription())
                        .boardStatus(board.getBoardStatus().getStatusDescription())
                        .likesCount(board.getLikesCount())
                        .viewCount(board.getViewCount())
                        .build())
                .collect(Collectors.toList());
    }

}