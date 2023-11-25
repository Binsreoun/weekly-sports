package com.weekly.sports.common.validator;

import static com.weekly.sports.common.meta.ResultCode.ALREADY_FOLLOWED;
import static com.weekly.sports.common.meta.ResultCode.NOT_EXIST_USER;
import static com.weekly.sports.common.meta.ResultCode.NOT_YET_FOLLOWED;

import com.weekly.sports.common.exception.GlobalException;
import com.weekly.sports.model.entity.FollowEntity;
import com.weekly.sports.model.entity.UserEntity;

public class UserValidator {

    public static void validator(UserEntity user) {
        if (!isExistUser(user)) {
            throw new GlobalException(NOT_EXIST_USER);
        }
    }

    public static void checkAlreadyFollowed(FollowEntity follow) {
        if (isExistFollow(follow)) {
            throw new GlobalException(ALREADY_FOLLOWED);
        }
    }

    public static void checkNotYetFollowed(FollowEntity follow) {
        if (!isExistFollow(follow)) {
            throw new GlobalException(NOT_YET_FOLLOWED);
        }
    }

    private static boolean isExistUser(UserEntity user) {
        return user != null;
    }

    private static boolean isExistFollow(FollowEntity follow) {
        return follow != null;
    }
}
