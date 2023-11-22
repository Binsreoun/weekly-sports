package com.weekly.sports.controller;

import com.weekly.sports.common.meta.ResultCode;
import com.weekly.sports.common.response.RestResponse;
import com.weekly.sports.model.dto.request.UserSignUpDto;
import com.weekly.sports.model.dto.response.SampleRes;
import com.weekly.sports.service.UserService;
import javax.naming.spi.DirStateFactory.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

  private final UserService userService;

  @PostMapping("/user/signup")
  public RestResponse<SampleRes> signUp(@RequestBody UserSignUpDto userSignUpDto)  {
    try {
      userService.signUp(userSignUpDto);
    }catch (IllegalArgumentException e){
      return RestResponse.error(ResultCode.SYSTEM_ERROR);
    }
    return RestResponse.success(SampleRes.builder().name("sign-up").text("회원가입 성공입니다.").build());
  }

}
