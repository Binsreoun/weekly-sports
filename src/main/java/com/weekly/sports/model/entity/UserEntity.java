package com.weekly.sports.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String introduction;
    @Enumerated(EnumType.STRING)
    private UserSocialEnum social;

    @OneToMany(mappedBy = "followingUserId", cascade = CascadeType.ALL)
    private List<FollowEntity> followEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "followerUserId", cascade = CascadeType.ALL)
    private List<FollowEntity> followerEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<BoardEntity> boardEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> commentEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<BoardLikeEntity> boardLikeEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<CommentLikeEntity> commentLikeEntities;
}
