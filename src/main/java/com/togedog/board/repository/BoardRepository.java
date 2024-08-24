package com.togedog.board.repository;

import com.togedog.board.entity.Board;
import com.togedog.board.entity.BoardType;
import com.togedog.pet.dto.PetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByBoardType(BoardType boardType, Pageable pageable);
}
