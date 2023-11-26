package com.weekly.sports.service;

import com.weekly.sports.common.validator.CommentValidator;
import com.weekly.sports.common.validator.UserValidator;
import com.weekly.sports.model.dto.request.comment.CommentLikeReq;
import com.weekly.sports.model.dto.response.comment.CommentLikeRes;
import com.weekly.sports.model.entity.CommentEntity;
import com.weekly.sports.model.entity.CommentLikeEntity;
import com.weekly.sports.model.entity.UserEntity;
import com.weekly.sports.repository.CommentLikeRepository;
import com.weekly.sports.repository.CommentRepository;
import com.weekly.sports.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeRes likeComment(CommentLikeReq commentLikeReq) {
        CommentEntity commentEntity = getCommentEntityByCommentId(commentLikeReq.getCommentId());
        UserEntity userEntity = getUserEntityByUserId(commentLikeReq.getUserId());

        CommentLikeEntity commentLikeEntity = getPrevCommentLike(commentEntity, userEntity);
        CommentValidator.checkAlreadyLiked(commentLikeEntity);

        commentLikeRepository.save(CommentLikeEntity.builder()
            .commentId(commentEntity)
            .userId(userEntity)
            .build());
        return new CommentLikeRes();
    }

    public CommentLikeRes unLikeComment(CommentLikeReq commentLikeReq) {
        CommentEntity commentEntity = getCommentEntityByCommentId(commentLikeReq.getCommentId());
        UserEntity userEntity = getUserEntityByUserId(commentLikeReq.getUserId());

        CommentLikeEntity commentLikeEntity = getPrevCommentLike(commentEntity, userEntity);
        CommentValidator.checkNotYetLiked(commentLikeEntity);

        commentLikeRepository.delete(commentLikeEntity);
        return new CommentLikeRes();
    }

    private CommentEntity getCommentEntityByCommentId(Long commentId) {
        CommentEntity comment = commentRepository.findByCommentId(commentId);
        CommentValidator.validator(comment);
        return comment;
    }

    private UserEntity getUserEntityByUserId(Long userId) {
        UserEntity user = userRepository.findByUserId(userId);
        UserValidator.validator(user);
        return user;
    }

    private CommentLikeEntity getPrevCommentLike(
        CommentEntity commentEntity, UserEntity userEntity) {
        return commentLikeRepository.findByCommentIdAndUserId(commentEntity, userEntity);
    }
}
