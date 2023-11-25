package com.weekly.sports.repository;

import com.weekly.sports.model.entity.BoardEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    //최신생성 기준 게시판 전체 조회
    List<BoardEntity> findAllByOrderByCreateTimestampDesc();
}
