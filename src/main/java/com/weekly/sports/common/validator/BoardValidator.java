package com.weekly.sports.common.validator;

import static com.weekly.sports.common.meta.ResultCode.ALREADY_LIKED_BOARD;
import static com.weekly.sports.common.meta.ResultCode.NOT_EXIST_BOARD;
import static com.weekly.sports.common.meta.ResultCode.NOT_YET_LIKED_BOARD;

import com.weekly.sports.common.exception.GlobalException;
import com.weekly.sports.model.entity.BoardEntity;
import com.weekly.sports.model.entity.BoardLikeEntity;

public class BoardValidator {

    public static void validate(BoardEntity board) {
        if (!isExistBoard(board)) {
            throw new GlobalException(NOT_EXIST_BOARD);
        }
    }

    public static void checkAlreadyLiked(BoardLikeEntity boardLike) {
        if (isExistBoardLike(boardLike)) {
            throw new GlobalException(ALREADY_LIKED_BOARD);
        }
    }

    public static void checkNotYetLiked(BoardLikeEntity boardLike) {
        if (!isExistBoardLike(boardLike)) {
            throw new GlobalException(NOT_YET_LIKED_BOARD);
        }
    }

    private static boolean isExistBoard(BoardEntity board) {
        return board != null;
    }

    private static boolean isExistBoardLike(BoardLikeEntity boardLike) {
        return boardLike != null;
    }
}
