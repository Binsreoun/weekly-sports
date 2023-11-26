package com.weekly.sports.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateReq {

    private Long userId;
    private String username;
    private String password;
    private String introduction;
}
