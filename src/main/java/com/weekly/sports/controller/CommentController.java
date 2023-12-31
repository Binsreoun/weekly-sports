package com.weekly.sports.controller;


import com.weekly.sports.common.response.RestResponse;
import com.weekly.sports.common.security.UserDetailsImpl;
import com.weekly.sports.model.dto.request.comment.CommentDeleteReq;
import com.weekly.sports.model.dto.request.comment.CommentLikeReq;
import com.weekly.sports.model.dto.request.comment.CommentSaveReq;
import com.weekly.sports.model.dto.request.comment.CommentUpdateReq;
import com.weekly.sports.model.dto.response.comment.CommentDeleteRes;
import com.weekly.sports.model.dto.response.comment.CommentLikeRes;
import com.weekly.sports.model.dto.response.comment.CommentSaveRes;
import com.weekly.sports.model.dto.response.comment.CommentUpdateRes;
import com.weekly.sports.service.CommentLikeService;
import com.weekly.sports.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final CommentLikeService commentLikeService;

    @PostMapping
    public RestResponse<CommentSaveRes> postComment(
        @RequestBody CommentSaveReq commentSaveReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentSaveReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(commentService.createComment(commentSaveReq));
    }

    @PutMapping
    public RestResponse<CommentUpdateRes> putComment(
        @RequestBody CommentUpdateReq commentUpdateReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentUpdateReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(commentService.updateComment(commentUpdateReq));
    }

    @DeleteMapping
    public RestResponse<CommentDeleteRes> deleteComment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentDeleteReq commentDeleteReq) {
        commentDeleteReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(commentService.deleteComment(commentDeleteReq));
    }

    @PostMapping("/like")
    public RestResponse<CommentLikeRes> likeComment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentLikeReq commentLikeReq) {
        commentLikeReq.setUserId(userDetails.getUser().getUserId());
        if (commentLikeReq.getIsLike()) {
            return RestResponse.success(commentLikeService.likeComment(commentLikeReq));
        } else {
            return RestResponse.success(commentLikeService.unLikeComment(commentLikeReq));
        }
    }
}
