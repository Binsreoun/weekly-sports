package com.weekly.sports.controller;


import com.weekly.sports.common.response.RestResponse;
import com.weekly.sports.common.security.UserDetailsImpl;
import com.weekly.sports.model.dto.request.CommentRequestDTO;
import com.weekly.sports.model.dto.response.CommentResponseDTO;
import com.weekly.sports.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public RestResponse<CommentResponseDTO> postComment(
        @RequestBody CommentRequestDTO commentRequestDTO,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestResponse.success(
            commentService.createComment(commentRequestDTO, userDetails.getUser()));
    }

    @PutMapping("/{commentId}")
    public RestResponse<CommentResponseDTO> putComment(
        @PathVariable Long commentId,
        @RequestBody CommentRequestDTO commentRequestDTO,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestResponse.success(
            commentService.updateComment(commentId, commentRequestDTO, userDetails.getUser()));
    }

    @DeleteMapping("/{commentId}")
    public RestResponse<CommentResponseDTO> deleteComment(
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestResponse.success(commentService.deleteComment(commentId, userDetails.getUser()));
    }

}
