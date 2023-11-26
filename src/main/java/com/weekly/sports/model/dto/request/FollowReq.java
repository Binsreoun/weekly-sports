package com.weekly.sports.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowReq {

    private Long followingUserId;
    private Long followerUserId;
    private Boolean isFollow;
}
