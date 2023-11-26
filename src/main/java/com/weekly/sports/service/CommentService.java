package com.weekly.sports.service;

import com.weekly.sports.model.dto.request.CommentRequestDTO;
import com.weekly.sports.model.dto.response.CommentResponseDTO;
import com.weekly.sports.model.entity.BoardEntity;
import com.weekly.sports.model.entity.CommentEntity;
import com.weekly.sports.model.entity.UserEntity;
import com.weekly.sports.repository.BoardRepository;
import com.weekly.sports.repository.CommentRepository;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentResponseDTO createComment(CommentRequestDTO dto, UserEntity user) {
        BoardEntity board = boardRepository.findByBoardId(dto.getTodoId());
        CommentEntity comment = CommentEntity.builder()
            .content(dto.getText())
            .boardEntity(board)
            .userEntity(user)
            .build();

        commentRepository.save(comment);
        return new CommentResponseDTO();
    }


    @Transactional
    public CommentResponseDTO updateComment(
        Long commentId, CommentRequestDTO commentRequestDTO, UserEntity user) {
        CommentEntity comment = getUserComment(commentId, user);
        // TODO ADD validator

        commentRepository.save(CommentEntity.builder()
            .CommentId(commentId)
            .content(commentRequestDTO.getText())
            .userEntity(comment.getUserEntity())
            .boardEntity(comment.getBoardEntity())
            .build());
        return new CommentResponseDTO();
    }

    public CommentResponseDTO deleteComment(Long commentId, UserEntity user) {
        CommentEntity comment = getUserComment(commentId, user);
        commentRepository.delete(comment);
        return new CommentResponseDTO();
    }

    private CommentEntity getUserComment(Long commentId, UserEntity user) {
        CommentEntity comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID입니다."));

        if (!user.getUserId().equals(comment.getUserEntity().getUserId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }
}
