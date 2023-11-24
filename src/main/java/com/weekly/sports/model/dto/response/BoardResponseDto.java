package com.weekly.sports.model.dto.response;

import com.weekly.sports.model.entity.BoardEntity;
import java.sql.Timestamp;
import lombok.Getter;

@Getter
public class BoardResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private Timestamp createTimestamp;

    public BoardResponseDto(BoardEntity saveBoard) {
        this.boardId = saveBoard.getBoardId();
        this.title = saveBoard.getTitle();
        this.content = saveBoard.getContent();
        this.createTimestamp = saveBoard.getCreateTimestamp();
    }
}
