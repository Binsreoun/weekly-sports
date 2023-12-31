package com.weekly.sports.model.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowEntityId implements Serializable {

    private Long followingUserId;
    private Long followerUserId;
}
