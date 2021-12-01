package com.vgreview.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vgreview.game.Game;
import com.vgreview.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Integer reviewId;
    private String review;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    Game game;
}
