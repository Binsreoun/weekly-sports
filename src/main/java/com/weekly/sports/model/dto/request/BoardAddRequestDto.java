package com.weekly.sports.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardAddRequestDto {

    //받는 내용
    private String title;
    private String content;
    private Long userId;
}
