package com.weekly.sports.model.dto.request.comment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentSaveReq {

    private Long boardId;
    private String content;
    private Long userId;
}
