package com.togedog.board.mapper;

import com.togedog.board.dto.BoardDto;
import com.togedog.board.entity.Board;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-20T16:26:51+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.22 (Azul Systems, Inc.)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board boardDtoPatchToBoard(BoardDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Board board = new Board();

        board.setBoardId( requestBody.getBoardId() );
        board.setTitle( requestBody.getTitle() );
        board.setContent( requestBody.getContent() );
        board.setContentImg( requestBody.getContentImg() );

        return board;
    }

    @Override
    public BoardDto.Response boardToBoardDtoResponse(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDto.Response.ResponseBuilder response = BoardDto.Response.builder();

        response.title( board.getTitle() );
        response.content( board.getContent() );
        response.contentImg( board.getContentImg() );

        return response.build();
    }

    @Override
    public List<BoardDto.Response> boardToBoardDtoResponses(List<Board> boards) {
        if ( boards == null ) {
            return null;
        }

        List<BoardDto.Response> list = new ArrayList<BoardDto.Response>( boards.size() );
        for ( Board board : boards ) {
            list.add( boardToBoardDtoResponse( board ) );
        }

        return list;
    }
}
