package com.weekly.sports.model.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRes {

    private Long userId;
    private String username;
    private String email;
    private String introduction;
    private int followerCnt;
}
