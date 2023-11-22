package com.weekly.sports.controller;

import static com.weekly.sports.common.meta.ResultCode.SYSTEM_ERROR;

import com.weekly.sports.common.exception.GlobalException;
import com.weekly.sports.common.response.RestResponse;
import com.weekly.sports.model.dto.response.SampleRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sample")
public class SampleController {

    @GetMapping
    public RestResponse<SampleRes> sampleMethod() {
        return RestResponse.success(SampleRes.builder().name("sample").text("샘플입니다.").build());
    }

    @GetMapping("/err")
    public RestResponse<?> sampleError() {
        throw new GlobalException(SYSTEM_ERROR);
    }
}
