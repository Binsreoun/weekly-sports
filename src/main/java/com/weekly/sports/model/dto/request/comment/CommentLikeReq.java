package com.weekly.sports.model.dto.request.comment;

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
public class CommentLikeReq {

    private Long userId;
    private Long commentId;
    private Boolean isLike;
}
