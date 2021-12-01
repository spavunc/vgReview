package com.vgreview.game;

import com.vgreview.rating.Rating;
import com.vgreview.review.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private Game game;
    private Rating rating;
    private Double averageRating;
    private List<Review> reviews;
}
