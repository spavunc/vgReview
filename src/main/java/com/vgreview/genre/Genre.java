package com.vgreview.genre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vgreview.game.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @Column(name="genre_id")
    @JsonProperty("id")
    private Long genreId;
    private String name;

    @ManyToMany(targetEntity = Game.class, mappedBy = "gameGenres")
    @JsonIgnoreProperties("gameGenres")
    List<Game> games;

}
