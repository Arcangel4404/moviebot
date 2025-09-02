package com.parinay.moviebot.controller;

import com.parinay.moviebot.model.Booking;
import com.parinay.moviebot.model.Movie;
import com.parinay.moviebot.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatbotController {

    private final MovieService movieService;

    public ChatbotController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/message")
    public Map<String, Object> chat(@RequestParam String text, @RequestParam(defaultValue = "User") String user) {
        Map<String, Object> response = new HashMap<>();
        text = text.toLowerCase();

        // Genre-based movie suggestions
        if (matchesAny(text, "sci-fi", "science fiction")) {
            List<Movie> movies = movieService.getMoviesByGenre("Sci-Fi");
            response.put("type", "suggestion");
            response.put("movies", movies);

        } else if (matchesAny(text, "romance")) {
            List<Movie> movies = movieService.getMoviesByGenre("Romance");
            response.put("type", "suggestion");
            response.put("movies", movies);

        } else if (matchesAny(text, "action")) {
            List<Movie> movies = movieService.getMoviesByGenre("Action");
            response.put("type", "suggestion");
            response.put("movies", movies);

        // View bookings (moved before book)
        } else if (matchesAny(text, "view bookings", "my bookings", "show bookings", "list bookings", "bookings")) {
            List<Booking> bookings = movieService.getAllBookings();
            response.put("type", "booking_list");
            response.put("bookings", bookings);

        // Booking logic
        } else if (matchesAny(text, "book movie", "book id", "book")) {
            try {
                Long id = extractMovieId(text);
                String result = movieService.bookMovie(id, user);
                response.put("type", "booking");
                response.put("message", result);
            } catch (Exception e) {
                response.put("type", "booking");
                response.put("status", "error");
                response.put("message", "Please use format: book movie id 2");
            }

        // Help fallback
        } else {
            response.put("type", "help");
            response.put("message", "Try:\n- Suggest me a Sci-Fi movie\n- Book movie id 2\n- View bookings");
        }

        return response;
    }

    // Match user input with possible keywords
    private boolean matchesAny(String input, String... keywords) {
        for (String keyword : keywords) {
            if (input.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    // Extract movie ID from message (e.g. "book movie id 2")
    private Long extractMovieId(String text) throws NumberFormatException {
        String[] words = text.split(" ");
        for (int i = words.length - 1; i >= 0; i--) {
            try {
                return Long.parseLong(words[i]);
            } catch (NumberFormatException ignored) {
            }
        }
        throw new NumberFormatException("No valid movie ID found");
    }
}
