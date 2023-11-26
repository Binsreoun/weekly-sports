package com.weekly.sports.controller;

import com.weekly.sports.common.response.RestResponse;
import com.weekly.sports.common.security.UserDetailsImpl;
import com.weekly.sports.model.dto.request.board.BoardAddRequestDto;
import com.weekly.sports.model.dto.request.board.BoardDeleteReq;
import com.weekly.sports.model.dto.request.board.BoardLikeReq;
import com.weekly.sports.model.dto.request.board.BoardUpdateRequestDto;
import com.weekly.sports.model.dto.response.board.BoardDeleteRes;
import com.weekly.sports.model.dto.response.board.BoardGetRes;
import com.weekly.sports.model.dto.response.board.BoardGetResList;
import com.weekly.sports.model.dto.response.board.BoardLikeRes;
import com.weekly.sports.model.dto.response.board.BoardSaveRes;
import com.weekly.sports.model.dto.response.board.BoardUpdateRes;
import com.weekly.sports.service.BoardLikeService;
import com.weekly.sports.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards")
public class BoardController {

    private final BoardService boardService;
    private final BoardLikeService boardLikeService;

    //게시글 작성
    @PostMapping
    public RestResponse<BoardSaveRes> addBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardAddRequestDto requestDto) {
        requestDto.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(boardService.addBoard(requestDto));
    }

    //게시글 단일 조회
    @GetMapping("/{boardId}")
    public RestResponse<BoardGetRes> getBoard(@PathVariable Long boardId) {
        return RestResponse.success(boardService.getBoard(boardId));
    }

    //게시글 전체 조회
    @GetMapping
    public RestResponse<BoardGetResList> getBoards() {
        return RestResponse.success(boardService.getBoards());
    }

    //게시글 수정
    @PatchMapping
    public RestResponse<BoardUpdateRes> updateBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardUpdateRequestDto requestDto) {
        requestDto.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(boardService.updateBoard(requestDto));
    }

    //게시글 삭제
    @DeleteMapping
    public RestResponse<BoardDeleteRes> deleteBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardDeleteReq boardDeleteReq) {
        boardDeleteReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(boardService.deleteBoard(boardDeleteReq));
    }

    @PostMapping("/like")
    public RestResponse<BoardLikeRes> likeBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BoardLikeReq boardLikeReq) {
        boardLikeReq.setUserId(userDetails.getUser().getUserId());
        if (boardLikeReq.getIsLike()) {
            return RestResponse.success(boardLikeService.likeBoard(boardLikeReq));
        } else {
            return RestResponse.success(boardLikeService.unLikeBoard(boardLikeReq));
        }
    }
}
