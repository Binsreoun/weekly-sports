package com.weekly.sports.repository;

import com.weekly.sports.model.entity.BoardEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    //최신생성 기준 게시판 전체 조회
    List<BoardEntity> findAllByOrderByCreateTimestampDesc();

    BoardEntity findByBoardId(Long boardId);

    BoardEntity findByBoardIdAndUserEntityUserId(Long boardId, Long userId);

    @Query(value = "select b from BoardEntity b "
        + "join FollowEntity f on b.userEntity.userId = f.followingUserId.userId "
        + "where f.followerUserId.userId = :userId "
        + "order by b.createTimestamp desc")
    List<BoardEntity> findAllFeedBoards(Long userId);
}
