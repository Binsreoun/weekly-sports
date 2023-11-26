package com.weekly.sports.controller;

import static com.weekly.sports.common.meta.ResultCode.EXIST_EMAIL;

import com.weekly.sports.common.response.RestResponse;
import com.weekly.sports.common.security.UserDetailsImpl;
import com.weekly.sports.model.dto.request.user.CheckUserReq;
import com.weekly.sports.model.dto.request.user.FollowReq;
import com.weekly.sports.model.dto.request.user.UserProfileReq;
import com.weekly.sports.model.dto.request.user.UserSignUpDto;
import com.weekly.sports.model.dto.request.user.UserUpdateReq;
import com.weekly.sports.model.dto.response.user.CheckUserRes;
import com.weekly.sports.model.dto.response.user.FollowRes;
import com.weekly.sports.model.dto.response.user.LoginRes;
import com.weekly.sports.model.dto.response.user.SignUpRes;
import com.weekly.sports.model.dto.response.user.UserProfileRes;
import com.weekly.sports.model.dto.response.user.UserUpdateRes;
import com.weekly.sports.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public RestResponse<SignUpRes> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        try {
            userService.signUp(userSignUpDto);
        } catch (IllegalArgumentException e) {
            return RestResponse.error(EXIST_EMAIL);
        }
        return RestResponse.success(
            SignUpRes.builder().name("sign-up").text("회원가입 성공입니다.").build());
    }

    @GetMapping("/login/oauth/{registrationId}")
    public RestResponse<LoginRes> googleLogin(@RequestParam String code,
        @PathVariable String registrationId,
        HttpServletResponse response) {
        String socialLogin = userService.socialLogin(code, registrationId, response);
        return RestResponse.success(
            LoginRes.builder().name("googleLogin").text(socialLogin).build());
    }

    @GetMapping
    public RestResponse<UserProfileRes> getUserProfile(@RequestBody UserProfileReq userProfileReq) {
        return RestResponse.success(userService.getUserProfile(userProfileReq));
    }

    @PostMapping("/follow")
    public RestResponse<FollowRes> followUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody FollowReq followReq) {
        followReq.setFollowerUserId(userDetails.getUser().getUserId());
        if (followReq.getIsFollow()) {
            return RestResponse.success(userService.followUser(followReq));
        } else {
            return RestResponse.success(userService.unFollowUser(followReq));
        }
    }

    @PutMapping
    public RestResponse<UserUpdateRes> updateUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UserUpdateReq userUpdateReq) {
        userUpdateReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(userService.updateUser(userUpdateReq));
    }

    @PostMapping("/check")
    public RestResponse<CheckUserRes> checkPassword(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CheckUserReq checkUserReq) {
        checkUserReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(userService.checkPassword(checkUserReq));
    }
}
