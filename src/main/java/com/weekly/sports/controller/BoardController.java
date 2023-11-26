package com.weekly.sports.controller;

import com.weekly.sports.common.response.RestResponse;
import com.weekly.sports.model.dto.request.BoardAddRequestDto;
import com.weekly.sports.model.dto.request.BoardLikeReq;
import com.weekly.sports.model.dto.request.BoardUpdateRequestDto;
import com.weekly.sports.model.dto.response.BoardLikeRes;
import com.weekly.sports.model.dto.response.BoardResponseDto;
import com.weekly.sports.model.dto.response.SampleRes;
import com.weekly.sports.security.UserDetailsImpl;
import com.weekly.sports.service.BoardLikeService;
import com.weekly.sports.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public RestResponse<BoardResponseDto> addBoard(@RequestBody BoardAddRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.addBoard(requestDto);
        return RestResponse.success(responseDto);
    }

    //게시글 단일 조회
    @GetMapping("/{boardId}")
    public RestResponse<BoardResponseDto> getBoard(@PathVariable Long boardId) {
        return RestResponse.success(boardService.getBoard(boardId));
    }

    //게시글 전체 조회
    @GetMapping
    public RestResponse<List<BoardResponseDto>> getBoards() {
        return RestResponse.success(boardService.getBoards());
    }

    //게시글 수정
    @PatchMapping("/{boardId}")
    public RestResponse<BoardResponseDto> updateBoard(@PathVariable Long boardId,
        @RequestBody BoardUpdateRequestDto requestDto) {
        return RestResponse.success(boardService.updateBoard(boardId, requestDto));
    }

    //게시글 삭제

    @DeleteMapping("/{boardId}")
    public RestResponse<SampleRes> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        //void타입이 리턴이 안되어 sample값을 넣었습니다.
        return RestResponse.success(SampleRes.builder().name("Delete").text("삭제되었습니다.").build());
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
