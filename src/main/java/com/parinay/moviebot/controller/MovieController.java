package com.parinay.moviebot.controller;

import com.parinay.moviebot.model.Movie;
import com.parinay.moviebot.service.MovieService;
import org.springframework.web.bind.annotation.*;
import com.parinay.moviebot.model.Booking;

import java.util.List;

@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/movies/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }

    @GetMapping("/movies/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return movieService.getAllBookings();
    }

    
}
