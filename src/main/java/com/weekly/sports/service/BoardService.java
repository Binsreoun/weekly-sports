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


    //게시글 작성service
    public BoardResponseDto addBoard(BoardAddRequestDto requestDto) {
        BoardEntity boardEntity = new BoardEntity(requestDto);
        BoardEntity saveBoard = boardRepository.save(boardEntity);
        return new BoardResponseDto(saveBoard);
    }

    //게시글 단일 조회 service
    public BoardResponseDto getBoard(Long boardId) {
        //Id없는 경우
        BoardEntity boardEntity = getBoardEntity(boardId);
        return new BoardResponseDto(boardEntity);
    }

    //게시글 전체 조회 service
    public List<BoardResponseDto> getBoards() {
        List<BoardResponseDto> responseDtoList = new ArrayList<>();

        List<BoardEntity> boardList = boardRepository.findAllByOrderByCreateTimestampDesc();
        for (BoardEntity boardEntity : boardList) {
            BoardResponseDto responseDto = new BoardResponseDto(boardEntity);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    //게시글 수정 service
    //인증 과정 빠진 상태입니다.
    @Transactional //데이터 변경 감지시 자동으로 변경
    public BoardResponseDto updateBoard(Long boardId, BoardUpdateRequestDto requestDto) {
        BoardEntity boardEntity = getBoardEntity(boardId);

        boardEntity.update(requestDto);

        return new BoardResponseDto(boardEntity);
    }

    //게시글 삭제 service
    //인증 과정 빠진 상태입니다.
    public void deleteBoard(Long boardId) {
        BoardEntity boardEntity = getBoardEntity(boardId);
        boardRepository.delete(boardEntity);
    }

    //공통된 부분 메서드 생성
    //Id 확인하여 게시판 유무 확인
    private BoardEntity getBoardEntity(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new NullPointerException("해당 게시글을 찾을 수 업습니다."));
    }
}
