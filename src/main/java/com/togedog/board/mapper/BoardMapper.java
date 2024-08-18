package com.togedog.board.mapper;

import com.togedog.board.dto.BoardDto;
import com.togedog.board.entity.Board;
import com.togedog.member.member.Member;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface BoardMapper {
    default Board boardDtoPostToBoard(BoardDto.Post requestBody){
        Member member = new Member();
        Board board = new Board();
        member.setMemberId(requestBody.getMemberId());
        board.setTitle(requestBody.getTitle());
        board.setContent(requestBody.getContent());
        board.setContent(requestBody.getContentImg());
        board.setBoardType(requestBody.getBoardType);
        board.setMember(member);
        return board;
    }

    Board boardDtoPatchToBoard(BoardDto.Patch requestBody);
    BoardDto.Response boardToBoardDtoResponse(Board board);
    List<BoardDto.Response> boardToBoardDtoResponses(List<Board> boards);

}
