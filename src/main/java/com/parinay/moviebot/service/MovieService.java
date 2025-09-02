package com.parinay.moviebot.service;

import com.parinay.moviebot.model.Movie;
import com.parinay.moviebot.model.Booking;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final List<Movie> movies = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private Long bookingIdCounter = 1L;

    public MovieService() {
        movies.add(new Movie(1L, "Inception", "Sci-Fi", 148));
        movies.add(new Movie(2L, "The Dark Knight", "Action", 152));
        movies.add(new Movie(3L, "Interstellar", "Sci-Fi", 169));
        movies.add(new Movie(4L, "La La Land", "Romance", 128));
        movies.add(new Movie(5L, "The Godfather", "Crime", 175));
        movies.add(new Movie(6L, "Titanic", "Romance", 195));
        movies.add(new Movie(7L, "The Matrix", "Sci-Fi", 136));
        movies.add(new Movie(8L, "Gladiator", "Action", 155));
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public List<Movie> getMoviesByGenre(String genre) {
        List<Movie> filtered = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                filtered.add(movie);
            }
        }
        return filtered;
    }

    public Movie getMovieById(Long id) {
        return movies.stream()
                     .filter(m -> m.getId().equals(id))
                     .findFirst()
                     .orElse(null);
    }

    public String bookMovie(Long movieId, String username) {
        Movie movie = getMovieById(movieId);
        if (movie == null) {
            return "Movie with ID " + movieId + " not found.";
        }

        Booking booking = new Booking(bookingIdCounter++, movie.getId(), movie.getTitle(), username);
        bookings.add(booking);

        return "Booked \"" + movie.getTitle() + "\" for " + username + " (Booking ID: " + booking.getBookingId() + ")";
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
}
