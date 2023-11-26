package com.weekly.sports.service;

import com.weekly.sports.common.validator.BoardValidator;
import com.weekly.sports.common.validator.CommentValidator;
import com.weekly.sports.common.validator.UserValidator;
import com.weekly.sports.model.dto.request.CommentDeleteReq;
import com.weekly.sports.model.dto.request.CommentSaveReq;
import com.weekly.sports.model.dto.request.CommentUpdateReq;
import com.weekly.sports.model.dto.response.CommentDeleteRes;
import com.weekly.sports.model.dto.response.CommentSaveRes;
import com.weekly.sports.model.dto.response.CommentUpdateRes;
import com.weekly.sports.model.entity.BoardEntity;
import com.weekly.sports.model.entity.CommentEntity;
import com.weekly.sports.model.entity.UserEntity;
import com.weekly.sports.repository.BoardRepository;
import com.weekly.sports.repository.CommentRepository;
import com.weekly.sports.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public CommentSaveRes createComment(CommentSaveReq dto) {
        UserEntity user = userRepository.findByUserId(dto.getUserId());
        UserValidator.validator(user);

        BoardEntity board = boardRepository.findByBoardId(dto.getBoardId());
        BoardValidator.validate(board);

        commentRepository.save(CommentEntity.builder()
            .content(dto.getContent())
            .boardEntity(board)
            .userEntity(user)
            .build());
        return new CommentSaveRes();
    }


    @Transactional
    public CommentUpdateRes updateComment(CommentUpdateReq commentUpdateReq) {
        CommentEntity comment = commentRepository.findByCommentIdAndUserEntityUserId(
            commentUpdateReq.getCommentId(), commentUpdateReq.getUserId());
        CommentValidator.validator(comment);

        commentRepository.save(CommentEntity.builder()
            .commentId(commentUpdateReq.getCommentId())
            .content(commentUpdateReq.getContent())
            .userEntity(comment.getUserEntity())
            .boardEntity(comment.getBoardEntity())
            .build());
        return new CommentUpdateRes();
    }

    public CommentDeleteRes deleteComment(CommentDeleteReq commentDeleteReq) {
        CommentEntity comment = commentRepository.findByCommentIdAndUserEntityUserId(
            commentDeleteReq.getCommentId(), commentDeleteReq.getUserId());
        CommentValidator.validator(comment);
        commentRepository.delete(comment);
        return new CommentDeleteRes();
    }
}
