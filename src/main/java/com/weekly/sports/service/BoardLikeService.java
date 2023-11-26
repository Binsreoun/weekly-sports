package com.weekly.sports.service;

import com.weekly.sports.common.validator.BoardValidator;
import com.weekly.sports.common.validator.UserValidator;
import com.weekly.sports.model.dto.request.board.BoardLikeReq;
import com.weekly.sports.model.dto.response.board.BoardLikeRes;
import com.weekly.sports.model.entity.BoardEntity;
import com.weekly.sports.model.entity.BoardLikeEntity;
import com.weekly.sports.model.entity.UserEntity;
import com.weekly.sports.repository.BoardLikeRepository;
import com.weekly.sports.repository.BoardRepository;
import com.weekly.sports.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardLikeRepository boardLikeRepository;

    public BoardLikeRes likeBoard(BoardLikeReq boardLikeReq) {
        BoardEntity boardEntity = getBoardEntityByBoardId(boardLikeReq.getBoardId());
        UserEntity userEntity = getUserEntityByUserId(boardLikeReq.getUserId());

        BoardLikeEntity boardLikeEntity = getPrevBoardLike(boardEntity, userEntity);
        BoardValidator.checkAlreadyLiked(boardLikeEntity);

        boardLikeRepository.save(BoardLikeEntity.builder()
            .boardId(boardEntity)
            .userId(userEntity)
            .build());
        return new BoardLikeRes();
    }

    public BoardLikeRes unLikeBoard(BoardLikeReq boardLikeReq) {
        BoardEntity boardEntity = getBoardEntityByBoardId(boardLikeReq.getBoardId());
        UserEntity userEntity = getUserEntityByUserId(boardLikeReq.getUserId());

        BoardLikeEntity boardLikeEntity = getPrevBoardLike(boardEntity, userEntity);
        BoardValidator.checkNotYetLiked(boardLikeEntity);

        boardLikeRepository.delete(boardLikeEntity);
        return new BoardLikeRes();
    }

    private BoardEntity getBoardEntityByBoardId(Long boardId) {
        BoardEntity board = boardRepository.findByBoardId(boardId);
        BoardValidator.validate(board);
        return board;
    }

    private UserEntity getUserEntityByUserId(Long userId) {
        UserEntity user = userRepository.findByUserId(userId);
        UserValidator.validator(user);
        return user;
    }

    private BoardLikeEntity getPrevBoardLike(BoardEntity boardEntity, UserEntity userEntity) {
        return boardLikeRepository.findByBoardIdAndUserId(boardEntity, userEntity);
    }
}
