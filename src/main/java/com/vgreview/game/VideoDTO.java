package com.vgreview.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vgreview.game.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {

    @JsonProperty("id")
    @Column(name = "api_id")
    private Long apiId;
    @JsonProperty("video_id")
    @Column(name = "video_id")
    private String videoId;

}
