package com.vgreview.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vgreview.game.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.vgreview.user.User;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue
    @Column(name = "rating_id")
    private Integer ratingId;
    private Integer score;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    Game game;
}
