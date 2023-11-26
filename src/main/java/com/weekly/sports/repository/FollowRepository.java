package com.weekly.sports.repository;

import com.weekly.sports.model.entity.FollowEntity;
import com.weekly.sports.model.entity.FollowEntityId;
import com.weekly.sports.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowEntity, FollowEntityId> {

    FollowEntity findByFollowerUserIdAndFollowingUserId(
        UserEntity followerUser,
        UserEntity followingUser);
}
