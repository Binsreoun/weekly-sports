package com.weekly.sports.model.dto.request.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeReq {

    private Long userId;
    private Long boardId;
    private Boolean isLike;
}
