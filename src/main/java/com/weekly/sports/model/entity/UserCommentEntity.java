package com.weekly.sports.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_comment")
public class UserCommentEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "commentId")
    private CommentEntity commentId;
}
