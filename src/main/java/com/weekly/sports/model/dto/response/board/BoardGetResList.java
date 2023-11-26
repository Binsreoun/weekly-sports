package com.weekly.sports.model.dto.response.board;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGetResList {

    private List<BoardGetRes> boardGetReses;
    private int total;
}
