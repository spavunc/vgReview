package com.vgreview.review;

import com.vgreview.game.Game;
import com.vgreview.rating.Rating;
import com.vgreview.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT * FROM Review WHERE game_game_id = ?1 ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Review> findRandomReviews(Long gameId);
    Review findByGameAndUser(Game game, User user);
}
