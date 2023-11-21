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
@Table(name = "follow")
public class FollowEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "followingUserId")
    private UserEntity followingUserId;

    @Id
    @ManyToOne
    @JoinColumn(name = "followerUserId")
    private UserEntity followerUserId;
}
