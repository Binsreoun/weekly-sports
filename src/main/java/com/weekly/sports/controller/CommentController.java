package com.weekly.sports.controller;


import com.weekly.sports.common.response.RestResponse;
import com.weekly.sports.common.security.UserDetailsImpl;
import com.weekly.sports.model.dto.request.CommentDeleteReq;
import com.weekly.sports.model.dto.request.CommentSaveReq;
import com.weekly.sports.model.dto.request.CommentUpdateReq;
import com.weekly.sports.model.dto.response.CommentDeleteRes;
import com.weekly.sports.model.dto.response.CommentSaveRes;
import com.weekly.sports.model.dto.response.CommentUpdateRes;
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

}
