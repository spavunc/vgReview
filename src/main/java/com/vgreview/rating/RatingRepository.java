package com.vgreview.rating;

import com.vgreview.game.Game;
import com.vgreview.review.Review;
import com.vgreview.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query(value="SELECT AVG(1.0 * score) FROM Rating WHERE game_game_id = ?1", nativeQuery = true)
    Double findAverageByGame(Long gameId);
    Rating findByGameAndUser(Game game, User user);
}
