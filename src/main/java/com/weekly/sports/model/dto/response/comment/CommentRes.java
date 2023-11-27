package com.weekly.sports.model.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRes {

    Long commentId;
    String content;
    String username;
    String createTimestamp;
    int like;
}
