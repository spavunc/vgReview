package com.vgreview.genre;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vgreview.api.ApiServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{

    ApiServiceImpl apiService;
    GenreRepository genreRepository;

    GenreServiceImpl(ApiServiceImpl apiService, GenreRepository genreRepository){
        this.apiService = apiService;
        this.genreRepository = genreRepository;
    }

    @Override
    public void saveGenres() throws JsonProcessingException {
        String uri = "https://api.igdb.com/v4/genres";
        RestTemplate restTemplate = apiService.getRestTemplateWithInterceptors();
        String body = "fields name;\n" +
                "limit 100;";
        ResponseEntity<String> result = restTemplate.postForEntity(uri, body, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Genre> genres = mapper.readValue(result.getBody(), new TypeReference<>(){});
        genreRepository.saveAll(genres);
    }

    @Override
    public List<Genre> findAll(){
        return genreRepository.findAll();
    }

}
