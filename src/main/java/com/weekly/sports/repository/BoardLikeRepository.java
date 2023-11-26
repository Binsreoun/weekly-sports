package com.weekly.sports.repository;

import com.weekly.sports.model.entity.BoardEntity;
import com.weekly.sports.model.entity.BoardLikeEntity;
import com.weekly.sports.model.entity.BoardLikeEntityId;
import com.weekly.sports.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, BoardLikeEntityId> {

    BoardLikeEntity findByBoardIdAndUserId(BoardEntity boardEntity, UserEntity userEntity);
}
