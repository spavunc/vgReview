package com.vgreview.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByApiId(Long apiId);
    @Query(value = "SELECT * FROM Game ORDER BY RAND() LIMIT 12", nativeQuery = true)
    List<Game> findRandomGames();
}
