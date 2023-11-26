package com.weekly.sports.service;

import com.weekly.sports.common.exception.GlobalException;
import com.weekly.sports.common.meta.ResultCode;
import com.weekly.sports.common.validator.BoardValidator;
import com.weekly.sports.common.validator.UserValidator;
import com.weekly.sports.model.dto.request.BoardAddRequestDto;
import com.weekly.sports.model.dto.request.BoardDeleteReq;
import com.weekly.sports.model.dto.request.BoardUpdateRequestDto;
import com.weekly.sports.model.dto.response.BoardDeleteRes;
import com.weekly.sports.model.dto.response.BoardResponseDto;
import com.weekly.sports.model.entity.BoardEntity;
import com.weekly.sports.model.entity.UserEntity;
import com.weekly.sports.repository.BoardRepository;
import com.weekly.sports.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //게시글 작성service
    public BoardResponseDto addBoard(BoardAddRequestDto requestDto) {
        UserEntity userEntity = getUserEntityByUserId(requestDto.getUserId());
        UserValidator.validator(userEntity);
        return new BoardResponseDto(
            boardRepository.save(BoardEntity.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .visit(0)
                .userEntity(userEntity)
                .build()));
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
    public BoardResponseDto updateBoard(BoardUpdateRequestDto requestDto) {
        BoardEntity prevBoard = boardRepository.findByBoardIdAndUserEntityUserId(
            requestDto.getBoardId(), requestDto.getUserId());
        BoardValidator.validate(prevBoard);

        return new BoardResponseDto(boardRepository.save(BoardEntity.builder()
            .boardId(prevBoard.getBoardId())
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .visit(prevBoard.getVisit())
            .userEntity(prevBoard.getUserEntity())
            .build()));
    }

    //게시글 삭제 service
    //인증 과정 빠진 상태입니다.
    public BoardDeleteRes deleteBoard(BoardDeleteReq boardDeleteReq) {
        BoardEntity boardEntity = boardRepository.findByBoardIdAndUserEntityUserId(
            boardDeleteReq.getBoardId(), boardDeleteReq.getUserId());
        BoardValidator.validate(boardEntity);
        boardRepository.delete(boardEntity);
        return new BoardDeleteRes();
    }

    //공통된 부분 메서드 생성
    //Id 확인하여 게시판 유무 확인
    private BoardEntity getBoardEntity(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new GlobalException(ResultCode.SYSTEM_ERROR));
    }

    private UserEntity getUserEntityByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }
}
