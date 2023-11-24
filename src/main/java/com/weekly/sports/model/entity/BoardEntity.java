package com.weekly.sports.model.entity;

import com.weekly.sports.model.dto.request.BoardAddRequestDto;
import com.weekly.sports.model.dto.request.BoardUpdateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    private String title;
    private String content;
    private int visit;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntities;

    public BoardEntity(BoardAddRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void update(BoardUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
