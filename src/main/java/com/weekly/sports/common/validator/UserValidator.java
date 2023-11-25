package com.weekly.sports.common.validator;

import static com.weekly.sports.common.meta.ResultCode.NOT_EXIST_USER;

import com.weekly.sports.common.exception.GlobalException;
import com.weekly.sports.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public static void validator(UserEntity user) {
        if (!isExistUser(user)) {
            throw new GlobalException(NOT_EXIST_USER);
        }
    }

    private static boolean isExistUser(UserEntity user) {
        return user != null;
    }

}
