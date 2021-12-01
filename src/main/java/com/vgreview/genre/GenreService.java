package com.vgreview.genre;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface GenreService {
    public void saveGenres() throws JsonProcessingException;
    public List<Genre> findAll();
}
