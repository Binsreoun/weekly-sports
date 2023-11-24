package com.weekly.sports.service;

import com.weekly.sports.model.dto.request.BoardAddRequestDto;
import com.weekly.sports.model.dto.request.BoardUpdateRequestDto;
import com.weekly.sports.model.dto.response.BoardResponseDto;
import com.weekly.sports.model.entity.BoardEntity;
import com.weekly.sports.repository.BoardRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional //데이터 변경 감지시 자동으로 변경
  public BoardResponseDto updateBoard(Long boardId, BoardUpdateRequestDto requestDto) {
    BoardEntity boardEntity = boardRepository.findById(boardId)
        .orElseThrow(() -> new NullPointerException("해당 게시글을 찾을 수 업습니다."));

    //인증 없이 바로 된다.
    boardEntity.update(requestDto);

    return new BoardResponseDto(boardEntity);
  }
}
