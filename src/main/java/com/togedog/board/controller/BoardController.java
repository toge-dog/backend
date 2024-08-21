package com.togedog.board.controller;

import com.togedog.board.dto.BoardDto;
import com.togedog.board.entity.Board;
import com.togedog.board.mapper.BoardMapper;
import com.togedog.board.service.BoardService;
import com.togedog.dto.MultiResponseDto;
import com.togedog.dto.SingleResponseDto;
import com.togedog.likes.dto.LikesDto;
import com.togedog.likes.mapper.LikesMapper;
import com.togedog.member.entity.Member;
import com.togedog.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/boards")
@Validated
@RequiredArgsConstructor
public class BoardController {
    private final static String BOARD_DEF_URL = "/boards";
    private final BoardMapper mapper;
    private final BoardService service;

    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody
                                    BoardDto.Post requestBody){
        Board createBoard =
                service.createBoard(mapper.boardDtoPostToBoard(requestBody));

        URI location = UriCreator.createUri(BOARD_DEF_URL, createBoard.getBoardId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{board-Id}")
    public ResponseEntity getBoard(@PathVariable("board-Id")
                                   @Positive long boardId) {
        Board findBoard = service.getBoard(service.findVerifiedBoard(boardId));
        if (findBoard.getBoardStatus() == Board.BoardStatus.BOARD_DELETED){
            return new ResponseEntity<>(new SingleResponseDto<>(null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.boardToBoardDtoResponse(findBoard)),
                HttpStatus.OK);
    }

    @PatchMapping("/{board-Id}")
    public ResponseEntity patchBoard(@PathVariable("board-Id") @Positive long boardId
            , @Valid @RequestBody BoardDto.Patch requestBody){
        requestBody.setBoardId(boardId);
        Board patchBoard = service.patchBoard(mapper.boardDtoPatchToBoard(requestBody));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.boardToBoardDtoResponse(patchBoard)),
                HttpStatus.OK);
    }

    @DeleteMapping ("/{board-Id}")
    public ResponseEntity deleteBoard(@PathVariable("board-Id") @Positive long boardId) {
        service.deleteBoard(boardId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity GetBoards(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size){
        Page<Board> pageBoards = service.findBoards(page-1,size);
        List<Board> boards = pageBoards.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.boardToBoardDtoResponses(boards),pageBoards),
                HttpStatus.OK);
    }
}
