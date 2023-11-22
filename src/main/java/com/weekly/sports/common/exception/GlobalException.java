package com.weekly.sports.common.exception;

import com.weekly.sports.common.meta.ResultCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private ResultCode resultCode;

    public GlobalException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
}
