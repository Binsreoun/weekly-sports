package com.weekly.sports.model.dto.request.comment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {

    private Long todoId;
    private String text;
    private Long userId;
}
