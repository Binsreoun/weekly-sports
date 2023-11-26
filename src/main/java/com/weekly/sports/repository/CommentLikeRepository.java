package com.weekly.sports.repository;

import com.weekly.sports.model.entity.CommentEntity;
import com.weekly.sports.model.entity.CommentLikeEntity;
import com.weekly.sports.model.entity.CommentLikeEntityId;
import com.weekly.sports.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends
    JpaRepository<CommentLikeEntity, CommentLikeEntityId> {

    CommentLikeEntity findByCommentIdAndUserId(CommentEntity commentEntity, UserEntity userEntity);
}
