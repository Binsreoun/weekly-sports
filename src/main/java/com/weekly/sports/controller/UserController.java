package com.weekly.sports.controller;

import com.weekly.sports.common.meta.ResultCode;
import com.weekly.sports.common.response.RestResponse;
import com.weekly.sports.model.dto.request.UserProfileReq;
import com.weekly.sports.model.dto.request.UserSignUpDto;
import com.weekly.sports.model.dto.response.SampleRes;
import com.weekly.sports.model.dto.response.UserProfileRes;
import com.weekly.sports.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public RestResponse<SampleRes> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        try {
            userService.signUp(userSignUpDto);
        } catch (IllegalArgumentException e) {
            return RestResponse.error(ResultCode.SYSTEM_ERROR);
        }
        return RestResponse.success(
            SampleRes.builder().name("sign-up").text("회원가입 성공입니다.").build());
    }

    @GetMapping("/user/login/oauth/{registrationId}")
    public RestResponse<SampleRes> googleLogin(@RequestParam String code,
        @PathVariable String registrationId,
        HttpServletResponse response) {
        String socialLogin = userService.socialLogin(code, registrationId, response);
        return RestResponse.success(
            SampleRes.builder().name("googleLogin").text(socialLogin).build());
    }

    @GetMapping("/user")
    public RestResponse<UserProfileRes> getUserProfile(@RequestBody UserProfileReq userProfileReq) {
        return RestResponse.success(userService.getUserProfile(userProfileReq));
    }
}
