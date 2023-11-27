package com.weekly.sports.model.dto.response.board;

import com.weekly.sports.model.dto.response.comment.CommentRes;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetRes {

    private Long boardId;
    private String title;
    private String content;
    private String createTimestamp;
    private String username;
    private int visit;
    private int like;
    private List<CommentRes> commentReses;
}
