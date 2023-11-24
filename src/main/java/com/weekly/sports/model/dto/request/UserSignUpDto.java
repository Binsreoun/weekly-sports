package com.weekly.sports.model.dto.request;

import lombok.Getter;

@Getter
public class UserSignUpDto {
  private String email;
  private String password;
  private String username;
}