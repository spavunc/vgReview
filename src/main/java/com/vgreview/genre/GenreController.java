package com.vgreview.genre;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vgreview.api.ApiServiceImpl;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/genres")
public class GenreController {

    ApiServiceImpl apiService;
    GenreServiceImpl genreService;

    GenreController(ApiServiceImpl apiService, GenreServiceImpl genreService){
        this.apiService = apiService;
        this.genreService = genreService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void saveGenresIfNotExist() throws JsonProcessingException {
        List<Genre> genres = genreService.findAll();
        if(genres.isEmpty()){
            genreService.saveGenres();
        }

    }

}
