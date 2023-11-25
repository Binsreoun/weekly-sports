package com.weekly.sports.common.response;

public class UserResponse extends RestResponse {

    UserResponse(Integer code, String message, Object data) {
        super(code, message, data);
    }
}
