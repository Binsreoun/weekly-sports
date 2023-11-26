package com.weekly.sports.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String content;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "boardId")
    private BoardEntity boardEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "commentId", cascade = CascadeType.ALL)
    private List<CommentLikeEntity> commentLikeEntities;
}
