package com.weekly.sports.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeReq {

    private Long userId;
    private Long boardId;
    private Boolean isLike;
}
