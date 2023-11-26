package com.weekly.sports.model.dto.request.board;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardUpdateRequestDto {

    private Long boardId;
    private String title;
    private String content;
    private Long userId;
}
