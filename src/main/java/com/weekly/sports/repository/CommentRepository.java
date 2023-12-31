package com.weekly.sports.repository;

import com.weekly.sports.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    CommentEntity findByCommentId(Long commentId);

    CommentEntity findByCommentIdAndUserEntityUserId(Long commentId, Long userId);
}
