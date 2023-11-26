package com.weekly.sports.common.validator;

import static com.weekly.sports.common.meta.ResultCode.ALREADY_LIKED_COMMENT;
import static com.weekly.sports.common.meta.ResultCode.NOT_EXIST_COMMENT;
import static com.weekly.sports.common.meta.ResultCode.NOT_YET_LIKED_COMMENT;

import com.weekly.sports.common.exception.GlobalException;
import com.weekly.sports.model.entity.CommentEntity;
import com.weekly.sports.model.entity.CommentLikeEntity;

public class CommentValidator {

    public static void validator(CommentEntity comment) {
        if (!isExistComment(comment)) {
            throw new GlobalException(NOT_EXIST_COMMENT);
        }
    }

    public static void checkAlreadyLiked(CommentLikeEntity commentLike) {
        if (isExistCommentLike(commentLike)) {
            throw new GlobalException(ALREADY_LIKED_COMMENT);
        }
    }

    public static void checkNotYetLiked(CommentLikeEntity commentLike) {
        if (!isExistCommentLike(commentLike)) {
            throw new GlobalException(NOT_YET_LIKED_COMMENT);
        }
    }

    private static boolean isExistComment(CommentEntity comment) {
        return comment != null;
    }

    private static boolean isExistCommentLike(CommentLikeEntity commentLike) {
        return commentLike != null;
    }
}
