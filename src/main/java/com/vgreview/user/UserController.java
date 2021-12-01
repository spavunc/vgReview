package com.vgreview.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    UserServiceImpl userService;
    UserRepository userRepository;

    UserController(UserServiceImpl userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestParam final String username, @RequestParam final String password
            , @RequestParam final String email){
        Optional<User> user = userRepository.findByUsernameOrEmail(username, email);
        if(user.isPresent()){
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
        userService.setUser(username, password, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
