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
        movies.add(new Movie(9L, "Forrest Gump", "Drama", 142));
        movies.add(new Movie(10L, "Pulp Fiction", "Crime", 154));
        movies.add(new Movie(11L, "The Shawshank Redemption", "Drama", 142));
        movies.add(new Movie(12L, "The Lord of the Rings: The Fellowship of the Ring", "Fantasy", 178));
        movies.add(new Movie(13L, "The Lord of the Rings: The Two Towers", "Fantasy", 179));
        movies.add(new Movie(14L, "The Lord of the Rings: The Return of the King", "Fantasy", 201));
        movies.add(new Movie(15L, "Star Wars: Episode IV - A New Hope", "Sci-Fi", 121));
        movies.add(new Movie(16L, "Star Wars: Episode V - The Empire Strikes Back", "Sci-Fi", 124));
        movies.add(new Movie(17L, "Star Wars: Episode VI - Return of the Jedi", "Sci-Fi", 131));
        movies.add(new Movie(18L, "Jurassic Park", "Adventure", 127));
        movies.add(new Movie(19L, "The Lion King", "Animation", 88));
        movies.add(new Movie(20L, "Finding Nemo", "Animation", 100));
        movies.add(new Movie(21L, "Toy Story", "Animation", 81));
        movies.add(new Movie(22L, "Braveheart", "History", 178));
        movies.add(new Movie(23L, "The Silence of the Lambs", "Thriller", 118));
        movies.add(new Movie(24L, "Se7en", "Thriller", 127));
        movies.add(new Movie(25L, "Fight Club", "Drama", 139));
        movies.add(new Movie(26L, "The Prestige", "Mystery", 130));
        movies.add(new Movie(27L, "Memento", "Mystery", 113));
        movies.add(new Movie(28L, "Avatar", "Sci-Fi", 162));
        movies.add(new Movie(29L, "Avengers: Endgame", "Action", 181));
        movies.add(new Movie(30L, "Black Panther", "Action", 134));
        movies.add(new Movie(31L, "Iron Man", "Action", 126));
        movies.add(new Movie(32L, "Spider-Man: Into the Spider-Verse", "Animation", 117));
        movies.add(new Movie(33L, "Coco", "Animation", 105));
        movies.add(new Movie(34L, "Up", "Animation", 96));
        movies.add(new Movie(35L, "Inside Out", "Animation", 95));
        movies.add(new Movie(36L, "The Notebook", "Romance", 123));
        movies.add(new Movie(37L, "A Walk to Remember", "Romance", 101));
        movies.add(new Movie(38L, "The Fault in Our Stars", "Romance", 126));
        movies.add(new Movie(39L, "Slumdog Millionaire", "Drama", 120));
        movies.add(new Movie(40L, "Life of Pi", "Adventure", 127));
        movies.add(new Movie(41L, "Cast Away", "Adventure", 143));
        movies.add(new Movie(42L, "The Revenant", "Adventure", 156));
        movies.add(new Movie(43L, "The Departed", "Crime", 151));
        movies.add(new Movie(44L, "Goodfellas", "Crime", 146));
        movies.add(new Movie(45L, "Joker", "Crime", 122));
        movies.add(new Movie(46L, "Her", "Romance", 126));
        movies.add(new Movie(47L, "Whiplash", "Drama", 106));
        movies.add(new Movie(48L, "Parasite", "Thriller", 132));
        movies.add(new Movie(49L, "The Grand Budapest Hotel", "Comedy", 99));
        movies.add(new Movie(50L, "Deadpool", "Comedy", 108));
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
