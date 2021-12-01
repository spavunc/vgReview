package com.vgreview.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameApi {

    private String name;
    @JsonProperty("id")
    private Long apiId;
    @JsonProperty("summary")
    private String description;
    @JsonProperty("first_release_date")
    private Long releaseDateMillis;
    private List<Long> genres;
    @JsonProperty
    private List<Long> screenshots;
    private List<Long> videos;
    @JsonProperty("aggregated_rating")
    private Integer rating;
    private Long cover;
    private String coverImageId;
}
