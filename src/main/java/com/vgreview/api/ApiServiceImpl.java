package com.vgreview.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vgreview.game.Game;
import com.vgreview.game.GameApi;
import com.vgreview.game.GameRepository;
import com.vgreview.genre.GenreRepository;
import com.vgreview.screenshot.Screenshot;
import com.vgreview.screenshot.ScreenshotRepository;
import com.vgreview.game.VideoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService{

    GenreRepository genreRepository;
    ScreenshotRepository screenshotRepository;
    GameRepository gameRepository;

    ApiServiceImpl(GenreRepository genreRepository, ScreenshotRepository screenshotRepository,
                   GameRepository gameRepository){
        this.genreRepository = genreRepository;
        this.screenshotRepository = screenshotRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public RestTemplate getRestTemplateWithInterceptors(){
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Client-ID", "smdsmnwngfqluvsto7bj96ivteup8w"));
        interceptors.add(new HeaderRequestInterceptor("Authorization", "Bearer 3jtaxrzqq8u96yx1dpr8cnuwxed9kn"));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Override
    public List<GameApi> getGamesData(RestTemplate restTemplate,
                                      String body, String uri) throws JsonProcessingException {
        ResponseEntity<String> result = restTemplate.postForEntity(uri, body, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(result.getBody(), new TypeReference<>(){});
    }

    @Override
    public List<Screenshot> getScreenshotData(RestTemplate restTemplate,
            String body, String uri) throws JsonProcessingException{
        ResponseEntity<String> result = restTemplate.postForEntity(uri, body, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(result.getBody(), new TypeReference<>(){});
    }

    @Override
    public String getVideoData(RestTemplate restTemplate,
                               String body, String uri) throws JsonProcessingException{
        ResponseEntity<String> result = restTemplate.postForEntity(uri, body, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<VideoDTO> videos = mapper.readValue(result.getBody(), new TypeReference<>(){});
        return  videos.get(0).getVideoId();
    }

    @Override
    public List<GameApi> setCovers(RestTemplate restTemplate, List<GameApi> games) throws JsonProcessingException{
        String id = "";
        String uri = "https://api.igdb.com/v4/covers";
        Long last = games.get(games.size() - 1).getApiId();
        List<GameApi> gamesWithCovers = new ArrayList<>();
        List<GameApi> gamesToReturn = new ArrayList<>();
        for(GameApi game : games){
            if(game.getCover() == null){
                game.setCover(Long.valueOf(0));
                gamesToReturn.add(game);
            }else {
                gamesWithCovers.add(game);
            }
            id = id + game.getCover();
            if(game.getApiId() != last){
                id = id + ", ";
            }
            if(game.getApiId() == last){
                id = id + ");";
            }
        }
        String body = "fields id, image_id;\n" +
                "where id = ("+ id;
        List<Screenshot> covers = getScreenshotData(restTemplate, body, uri);
        for(Screenshot cover : covers){
            for(GameApi game : gamesWithCovers){
                if(game.getCover().equals(cover.getApiId()) && game.getCover() != 0){
                    game.setCoverImageId(cover.getImageId());
                }else if (game.getCover() == 0){
                    game.setCoverImageId("nocover");
                }
            }
        }
        //puts games with covers on the front
        gamesToReturn.addAll(0, gamesWithCovers);
        return gamesToReturn;
    }

    @Override
    public Game mapGameToApi(RestTemplate restTemplate, GameApi gameApi) throws JsonProcessingException {
        Game game = new Game();
        game.setApiId(gameApi.getApiId());
        game.setName(gameApi.getName());
        String description = gameApi.getDescription();
        game.setDescription(description);
        game.setProfilePicture(gameApi.getCoverImageId());
        game.setScore(gameApi.getRating());
        if(gameApi.getGenres() != null && !gameApi.getGenres().isEmpty()) {
            game.setGameGenres(genreRepository.findAllById(gameApi.getGenres()));
        }
        if(gameApi.getVideos() != null && !gameApi.getVideos().isEmpty()) {
            String video = getVideo(restTemplate, gameApi);
            game.setVideo(video);
        }
        if(gameApi.getScreenshots() != null && !gameApi.getScreenshots().isEmpty()) {
            List<Screenshot> screenshots = getScreenShots(restTemplate, gameApi);
            game.setGameScreenshots(screenshots);
            gameRepository.save(game);
            for (Screenshot screenshot : screenshots) {
                screenshot.setGame(game);
            }
            screenshotRepository.saveAll(screenshots);
        }
        return game;
    }

    @Override
    public List<Screenshot> getScreenShots(RestTemplate restTemplate, GameApi game) throws JsonProcessingException {
        String id = "";
        String uri = "https://api.igdb.com/v4/screenshots";
        if(game.getScreenshots().size() > 1) {
            Long last = game.getScreenshots().get(game.getScreenshots().size() - 1);
            for (Long screenshot : game.getScreenshots()) {
                if (screenshot != last) {
                    id = id + screenshot + ", ";
                }
                if (screenshot == last) {
                    id = id + screenshot + ");";
                }
            }
        }else{
            id = game.getScreenshots().get(0) + ");";
        }
        String body = "fields id, image_id;\n" +
                "where id = ("+ id;
        return getScreenshotData(restTemplate, body, uri);
    }

    @Override
    public String getVideo(RestTemplate restTemplate, GameApi game) throws JsonProcessingException {
        String uri = "https://api.igdb.com/v4/game_videos";
        String body = "fields video_id;\n" +
                "where id = " + game.getVideos().get(0) + ";";
        return getVideoData(restTemplate, body, uri);
    }

}
