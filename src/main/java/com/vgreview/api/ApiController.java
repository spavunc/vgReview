package com.vgreview.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vgreview.game.Game;
import com.vgreview.game.GameApi;
import com.vgreview.game.GameDTO;
import com.vgreview.game.GameRepository;
import com.vgreview.rating.Rating;
import com.vgreview.rating.RatingRepository;
import com.vgreview.review.ReviewRepository;
import com.vgreview.user.User;
import com.vgreview.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    ApiServiceImpl apiService;
    GameRepository gameRepository;
    RatingRepository ratingRepository;
    UserRepository userRepository;
    ReviewRepository reviewRepository;

    ApiController(ApiServiceImpl apiService, GameRepository gameRepository, RatingRepository ratingRepository,
                  UserRepository userRepository, ReviewRepository reviewRepository){
        this.apiService = apiService;
        this.gameRepository = gameRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping(params = "id")
    public ResponseEntity<GameDTO> saveGameData(@RequestParam final Long id) throws JsonProcessingException {
        String body = "fields cover, genres, name, first_release_date, aggregated_rating, screenshots, summary, videos;\n" +
                "where id = " + id + ";";
        String uri = "https://api.igdb.com/v4/games";
        Game existingGame = gameRepository.findByApiId(id);
        if(existingGame == null) {
            RestTemplate restTemplate = apiService.getRestTemplateWithInterceptors();
            List<GameApi> gameApi = apiService.getGamesData(restTemplate, body, uri);
            if(!gameApi.isEmpty()){
                gameApi = apiService.setCovers(restTemplate, gameApi);
            }
            existingGame = apiService.mapGameToApi(restTemplate, gameApi.get(0));
            GameDTO gameDTO = new GameDTO(existingGame, null, null, null);
            return new ResponseEntity<>(gameDTO, HttpStatus.OK);
        }else{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Rating currentRating = null;
            if(principal != "anonymousUser"){
                UserDetails userDetails = (UserDetails) principal;
                Optional<User> user = userRepository.findOneByUsername(userDetails.getUsername());
                currentRating = ratingRepository.findByGameAndUser(existingGame, user.get());
            }
            GameDTO gameDTO = new GameDTO(existingGame, currentRating,
                    ratingRepository.findAverageByGame(existingGame.getGameId()),
                    reviewRepository.findRandomReviews(existingGame.getGameId()));
            return new ResponseEntity<>(gameDTO, HttpStatus.OK);
        }
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<GameApi>> findSuggestions(@RequestParam final String name) throws JsonProcessingException {
        List<GameApi> games = new ArrayList<>();
        if(name.isEmpty()){
            return new ResponseEntity<>(games, HttpStatus.OK);
        }
        String body = "fields cover, name;\n" +
                "search \"" + name + "\";\n" +
                "limit 5;";
        String uri = "https://api.igdb.com/v4/games";
        RestTemplate restTemplate = apiService.getRestTemplateWithInterceptors();
        games = apiService.getGamesData(restTemplate, body, uri);
        if(!games.isEmpty()) {
            games = apiService.setCovers(restTemplate, games);
        }
        return new ResponseEntity<>(games, HttpStatus.OK);
    }


}
