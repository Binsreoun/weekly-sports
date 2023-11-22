package com.weekly.sports.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class LoginRequestDto {

  private String email;
  private String password;
}
