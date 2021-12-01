package com.vgreview.game;

import com.vgreview.rating.Rating;
import com.vgreview.rating.RatingRepository;
import com.vgreview.review.Review;
import com.vgreview.review.ReviewRepository;
import com.vgreview.user.User;
import com.vgreview.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game")
public class GameController {

    GameRepository gameRepository;
    UserRepository userRepository;
    ReviewRepository reviewRepository;
    RatingRepository ratingRepository;

    GameController(GameRepository gameRepository, UserRepository userRepository, ReviewRepository reviewRepository,
                   RatingRepository ratingRepository){
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.ratingRepository = ratingRepository;
    }

    @PostMapping("/review")
    public ResponseEntity<Game> review(@RequestParam final Long id, @RequestParam String reviewText){
        Optional<Game> game = gameRepository.findById(id);
        Optional<User> user;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != "anonymousUser"){
            UserDetails userDetails = (UserDetails) principal;
            user = userRepository.findOneByUsername(userDetails.getUsername());
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(game.isPresent()) {
            Review review = reviewRepository.findByGameAndUser(game.get(), user.get());
            if(review == null){
                review = new Review();
                review.setReview(reviewText);
                review.setGame(game.get());
                review.setUser(user.get());
            }else{
                review.setReview(reviewText);
            }
            reviewRepository.save(review);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rate")
    public ResponseEntity rate(@RequestParam final Long id, @RequestParam Integer score){
        Optional<Game> game = gameRepository.findById(id);
        Optional<User> user;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != "anonymousUser"){
            UserDetails userDetails = (UserDetails) principal;
            user = userRepository.findOneByUsername(userDetails.getUsername());
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(game.isPresent()) {
            Rating rating = ratingRepository.findByGameAndUser(game.get(), user.get());
            if(rating == null){
                rating = new Rating();
                rating.setScore(score);
                rating.setUser(user.get());
                rating.setGame(game.get());
            }else{
                rating.setScore(score);
            }
            ratingRepository.save(rating);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/showcase")
    public ResponseEntity<List<Game>> getGameShowcase(){
        return new ResponseEntity<>(gameRepository.findRandomGames(), HttpStatus.OK);
    }
}
