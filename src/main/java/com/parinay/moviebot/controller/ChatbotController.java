package com.parinay.moviebot.controller;

import com.parinay.moviebot.model.Booking;
import com.parinay.moviebot.model.Movie;
import com.parinay.moviebot.service.MovieService;
import com.parinay.moviebot.service.GeminiChatService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatbotController {

    private final MovieService movieService;
    private final GeminiChatService geminiChatService;
    private final Map<String, List<String>> userHistories = new HashMap<>();

    public ChatbotController(MovieService movieService, GeminiChatService geminiChatService) {
        this.movieService = movieService;
        this.geminiChatService = geminiChatService;
    }

    @GetMapping("/message")
    public Map<String, Object> chat(@RequestParam String text, @RequestParam(defaultValue = "User") String user) {
        Map<String, Object> response = new HashMap<>();
        String lowerText = text.toLowerCase();

        // 1. View bookings logic (handled by backend)
        if (matchesAny(lowerText, "view bookings", "my bookings", "show bookings", "list bookings", "bookings")) {
            List<Booking> bookings = movieService.getAllBookings();
            response.put("type", "booking_list");
            response.put("bookings", bookings);
            return response;
        }

        // 2. Try to detect booking intent and movie name in user message
        List<Movie> movies = movieService.getAllMovies();
        Movie bookedMovie = null;
        for (Movie m : movies) {
            if (lowerText.contains(m.getTitle().toLowerCase()) &&
                matchesAny(lowerText, "book", "reserve", "ticket", "buy")) {
                bookedMovie = m;
                break;
            }
        }

        if (bookedMovie != null) {
            String result = movieService.bookMovie(bookedMovie.getId(), user);
            response.put("type", "booking");
            response.put("message", "Booking confirmed for " + bookedMovie.getTitle() + ".\n" + result);
            return response;
        }

        // 3. For all other queries, use Gemini with movie list context
        StringBuilder movieList = new StringBuilder();
        for (Movie m : movies) {
            movieList.append("- ").append(m.getTitle());
            if (m.getGenre() != null) movieList.append(" (").append(m.getGenre()).append(")");
            movieList.append(", id: ").append(m.getId());
            movieList.append("\n");
        }

        List<String> history = userHistories.computeIfAbsent(user, k -> new ArrayList<>());
        history.add("User: " + text);

        String systemPrompt = "You are a helpful movie assistant. " +
    "Only recommend or book movies from this list:\n" + movieList +
    "If the user asks for a suggestion or booking, use only this list. " +
    "If asked about details (like IMDB score, director, etc.) about a movie in the list, answer if you know, or use your own knowledge if not provided. " +
    "If you don't know, politely say you don't have that information. " +
    "Do not recommend or book movies not in the list. " +
    "Do not use Emojis in your responses.";

        List<String> fullHistory = new ArrayList<>();
        fullHistory.add("System: " + systemPrompt);
        fullHistory.addAll(history);

        String geminiReply = geminiChatService.chatWithGemini(text, fullHistory);
        history.add("Bot: " + geminiReply);

        response.put("type", "gemini");
        response.put("message", geminiReply);
        return response;
    }

    private boolean matchesAny(String input, String... keywords) {
        for (String keyword : keywords) {
            if (input.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}