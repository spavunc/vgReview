package com.vgreview.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vgreview.game.Game;
import com.vgreview.game.GameApi;
import com.vgreview.screenshot.Screenshot;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public interface ApiService {
    RestTemplate getRestTemplateWithInterceptors();
    List<GameApi> getGamesData(RestTemplate restTemplate, String body, String uri) throws JsonProcessingException;
    List<Screenshot> getScreenshotData(RestTemplate restTemplate,
                                       String body, String uri) throws JsonProcessingException;

    String getVideoData(RestTemplate restTemplate,
                        String body, String uri) throws JsonProcessingException;

    List<GameApi> setCovers(RestTemplate restTemplate, List<GameApi> games) throws JsonProcessingException;

    Game mapGameToApi(RestTemplate restTemplate, GameApi gameApi) throws JsonProcessingException;

    List<Screenshot> getScreenShots(RestTemplate restTemplate, GameApi game) throws JsonProcessingException;

    String getVideo(RestTemplate restTemplate, GameApi game) throws JsonProcessingException;
}
