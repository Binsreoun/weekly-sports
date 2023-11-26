package com.weekly.sports.common.validator;

import static com.weekly.sports.common.meta.ResultCode.NOT_EXIST_COMMENT;

import com.weekly.sports.common.exception.GlobalException;
import com.weekly.sports.model.entity.CommentEntity;

public class CommentValidator {

    public static void validator(CommentEntity comment) {
        if (!isExistComment(comment)) {
            throw new GlobalException(NOT_EXIST_COMMENT);
        }
    }

    private static boolean isExistComment(CommentEntity comment) {
        return comment != null;
    }
}
