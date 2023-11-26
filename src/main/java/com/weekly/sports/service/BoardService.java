package com.weekly.sports.service;

import static com.weekly.sports.common.meta.ResultCode.NOT_EXIST_BOARD;
import static com.weekly.sports.common.utils.BoardUtil.timestampToString;

import com.weekly.sports.common.exception.GlobalException;
import com.weekly.sports.common.validator.BoardValidator;
import com.weekly.sports.common.validator.UserValidator;
import com.weekly.sports.model.dto.request.board.BoardAddRequestDto;
import com.weekly.sports.model.dto.request.board.BoardDeleteReq;
import com.weekly.sports.model.dto.request.board.BoardUpdateRequestDto;
import com.weekly.sports.model.dto.request.board.FeedBoardReq;
import com.weekly.sports.model.dto.response.board.BoardDeleteRes;
import com.weekly.sports.model.dto.response.board.BoardGetRes;
import com.weekly.sports.model.dto.response.board.BoardGetResList;
import com.weekly.sports.model.dto.response.board.BoardSaveRes;
import com.weekly.sports.model.dto.response.board.BoardUpdateRes;
import com.weekly.sports.model.dto.response.comment.CommentRes;
import com.weekly.sports.model.entity.BoardEntity;
import com.weekly.sports.model.entity.CommentEntity;
import com.weekly.sports.model.entity.UserEntity;
import com.weekly.sports.repository.BoardRepository;
import com.weekly.sports.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    //게시글 작성service
    public BoardSaveRes addBoard(BoardAddRequestDto requestDto) {
        UserEntity userEntity = getUserEntityByUserId(requestDto.getUserId());
        UserValidator.validator(userEntity);
        return BoardServiceMapper.INSTANCE.toBoardSaveRes(
            boardRepository.save(BoardEntity.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .visit(0)
                .userEntity(userEntity)
                .build()));
    }

    @Transactional
    public BoardGetRes getBoard(Long boardId) {
        BoardEntity board = getBoardEntity(boardId);
        addVisitBoard(board);
        return BoardServiceMapper.INSTANCE.toBoardGetRes(board);
    }

    @Transactional
    public void addVisitBoard(BoardEntity board) {
        board.setVisit(board.getVisit() + 1);
    }

    //게시글 전체 조회 service
    public BoardGetResList getBoards() {
        List<BoardGetRes> boardGetReses = BoardServiceMapper.INSTANCE.toBoardGetReses(
            boardRepository.findAllByOrderByCreateTimestampDesc());

        return BoardGetResList.builder()
            .boardGetReses(boardGetReses)
            .total(boardGetReses.size())
            .build();
    }

    public BoardGetResList getFeedBoards(FeedBoardReq feedBoardReq) {
        List<BoardGetRes> boardGetReses = BoardServiceMapper.INSTANCE.toBoardGetReses(
            boardRepository.findAllFeedBoards(feedBoardReq.getUserId()));

        return BoardGetResList.builder()
            .boardGetReses(boardGetReses)
            .total(boardGetReses.size())
            .build();
    }

    //게시글 수정 service
    //인증 과정 빠진 상태입니다.
    @Transactional //데이터 변경 감지시 자동으로 변경
    public BoardUpdateRes updateBoard(BoardUpdateRequestDto requestDto) {
        BoardEntity prevBoard = boardRepository.findByBoardIdAndUserEntityUserId(
            requestDto.getBoardId(), requestDto.getUserId());
        BoardValidator.validate(prevBoard);

        boardRepository.save(BoardEntity.builder()
            .boardId(prevBoard.getBoardId())
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .visit(prevBoard.getVisit())
            .userEntity(prevBoard.getUserEntity())
            .build());
        entityManager.clear();

        BoardEntity board = boardRepository.findByBoardId(requestDto.getBoardId());
        return BoardServiceMapper.INSTANCE.toBoardUpdateRes(board);
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
            .orElseThrow(() -> new GlobalException(NOT_EXIST_BOARD));
    }

    private UserEntity getUserEntityByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Mapper
    public interface BoardServiceMapper {

        BoardServiceMapper INSTANCE = Mappers.getMapper(BoardServiceMapper.class);

        @Mapping(source = "createTimestamp", target = "createTimestamp")
        default String toStringTime(Timestamp timestamp) {
            return timestampToString(timestamp);
        }

        @Mapping(source = "userEntity", target = "username")
        default String toUsername(UserEntity userEntity) {
            return userEntity.getUsername();
        }

        @Mapping(source = "createTimestamp", target = "createTimestamp")
        @Mapping(source = "userEntity", target = "username")
        BoardSaveRes toBoardSaveRes(BoardEntity board);

        @Mapping(source = "commentEntities", target = "commentReses")
        @Mapping(source = "createTimestamp", target = "createTimestamp")
        @Mapping(source = "userEntity", target = "username")
        BoardUpdateRes toBoardUpdateRes(BoardEntity board);

        @Mapping(source = "userEntity", target = "username")
        CommentRes toCommentRes(CommentEntity commentEntity);

        @Mapping(source = "userEntity", target = "username")
        @Mapping(source = "createTimestamp", target = "createTimestamp")
        List<CommentRes> toCommentReses(List<CommentEntity> commentEntities);

        @Mapping(source = "commentEntities", target = "commentReses")
        @Mapping(source = "createTimestamp", target = "createTimestamp")
        @Mapping(source = "userEntity", target = "username")
        BoardGetRes toBoardGetRes(BoardEntity board);

        List<BoardGetRes> toBoardGetReses(List<BoardEntity> boardEntities);
    }
}
