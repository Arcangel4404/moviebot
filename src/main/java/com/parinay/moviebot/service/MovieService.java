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
        // Mock movie data
        movies.add(new Movie(1L, "Inception", "Sci-Fi", 148));
        movies.add(new Movie(2L, "The Dark Knight", "Action", 152));
        movies.add(new Movie(3L, "Interstellar", "Sci-Fi", 169));
        movies.add(new Movie(4L, "La La Land", "Romance", 128));
        movies.add(new Movie(5L, "The Godfather", "Crime", 175));
    }

    // Get all movies
    public List<Movie> getAllMovies() {
        return movies;
    }

    // Get movies by genre
    public List<Movie> getMoviesByGenre(String genre) {
        List<Movie> filtered = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                filtered.add(movie);
            }
        }
        return filtered;
    }

    // Get movie by ID
    public Movie getMovieById(Long id) {
        return movies.stream()
                     .filter(m -> m.getId().equals(id))
                     .findFirst()
                     .orElse(null);
    }

    // Book a movie
    public String bookMovie(Long movieId, String username) {
        Movie movie = getMovieById(movieId);
        if (movie == null) {
            return "❌ Movie with ID " + movieId + " not found.";
        }

        Booking booking = new Booking(bookingIdCounter++, movie.getId(), movie.getTitle(), username);
        bookings.add(booking);

        return "✅ Booked \"" + movie.getTitle() + "\" for " + username + " (Booking ID: " + booking.getBookingId() + ")";
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookings;
    }
}
