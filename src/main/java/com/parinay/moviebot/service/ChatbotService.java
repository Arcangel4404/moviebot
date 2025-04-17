package com.parinay.moviebot.service;

import com.parinay.moviebot.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatbotService {

    private final MovieService movieService;

    @Autowired
    public ChatbotService(MovieService movieService) {
        this.movieService = movieService;
    }

    // Suggest movies based on user input (genre)
    public String suggestMovies(String genre) {
        List<Movie> movies;
        if (genre == null || genre.isEmpty()) {
            movies = movieService.getAllMovies();
        } else {
            movies = movieService.getMoviesByGenre(genre);
        }

        if (movies.isEmpty()) {
            return "Sorry, no movies found in this genre.";
        } else {
            StringBuilder suggestion = new StringBuilder("Here are some movie suggestions:\n");
            for (Movie movie : movies) {
                suggestion.append(movie.getTitle())
                        .append(" (Genre: ").append(movie.getGenre())
                        .append(", Duration: ").append(movie.getDuration()).append(" mins)\n");
            }
            return suggestion.toString();
        }
    }

    // Book a movie based on ID
    public String bookMovie(Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            return "Sorry, the movie with ID " + movieId + " does not exist.";
        } else {
            return "You have successfully booked the movie: " + movie.getTitle() + ". Enjoy your show!";
        }
    }
}
