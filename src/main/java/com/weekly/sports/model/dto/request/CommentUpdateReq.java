package com.weekly.sports.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentUpdateReq {

    private Long commentId;
    private String content;
    private Long userId;
}
