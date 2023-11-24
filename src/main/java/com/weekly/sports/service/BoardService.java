package com.weekly.sports.service;

import com.weekly.sports.model.dto.request.BoardAddRequestDto;
import com.weekly.sports.model.dto.response.BoardResponseDto;
import com.weekly.sports.model.entity.BoardEntity;
import com.weekly.sports.repository.BoardRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  public BoardResponseDto addBoard(BoardAddRequestDto requestDto) {
    BoardEntity boardEntity = new BoardEntity(requestDto);
    BoardEntity saveBoard = boardRepository.save(boardEntity);
    return new BoardResponseDto(saveBoard);
  }

  public BoardResponseDto getBoard(Long boardId) {
    //Id없는 경우
    BoardEntity boardEntity = boardRepository.findById(boardId)
        .orElseThrow(() -> new NullPointerException("해당 게시글을 찾을 수 업습니다."));

    return new BoardResponseDto(boardEntity);
  }

  public List<BoardResponseDto> getBoards() {
    List<BoardResponseDto> responseDtoList = new ArrayList<>();

    List<BoardEntity> boardList = boardRepository.findAllByOrderByCreateTimestampDesc();
    for (BoardEntity boardEntity : boardList) {
      BoardResponseDto responseDto = new BoardResponseDto(boardEntity);
      responseDtoList.add(responseDto);
    }

    return responseDtoList;
  }
}
