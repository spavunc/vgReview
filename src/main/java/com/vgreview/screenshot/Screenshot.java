package com.vgreview.screenshot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vgreview.game.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Screenshot {

    @Id
    @GeneratedValue
    @Column(name = "screenshot_id")
    private Long screenshotId;
    @JsonProperty("id")
    @Column(name = "api_id")
    private Long apiId;
    @JsonProperty("image_id")
    @Column(name = "image_id")
    private String imageId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    Game game;
}
