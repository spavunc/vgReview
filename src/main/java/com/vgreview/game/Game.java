package com.vgreview.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgreview.genre.Genre;
import com.vgreview.rating.Rating;
import com.vgreview.review.Review;
import com.vgreview.screenshot.Screenshot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue
    @Column(name = "game_id")
    private Long gameId;
    private String name;
    @Column(name = "api_id")
    private Long apiId;
    @Column(name = "profile_picture")
    private String profilePicture;
    @Lob
    private String description;
    private Integer score;
    private String video;


    @OneToMany(mappedBy = "game")
    List<Review> gameReviews;

    @OneToMany(mappedBy = "game")
    List<Rating> gameRatings;

    @OneToMany(mappedBy = "game")
    List<Screenshot> gameScreenshots;

    @JsonIgnoreProperties("games")
    @ManyToMany(targetEntity = Genre.class, fetch=FetchType.EAGER)
    @JoinTable(name="Game_Genre",
            joinColumns = @JoinColumn(name="game_id"),
            inverseJoinColumns = @JoinColumn(name="genre_id"))
    List<Genre> gameGenres;

}
