package com.parinay.moviebot.controller;

import com.parinay.moviebot.model.Booking;
import com.parinay.moviebot.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin // allow frontend to make calls
public class ChatbotController {

    private final MovieService movieService;

    public ChatbotController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/message")
    public String chat(@RequestParam String text, @RequestParam(defaultValue = "User") String user) {
        text = text.toLowerCase();

        if (text.contains("sci-fi")) {
            return "🧠 Try these Sci-Fi movies: Inception, Interstellar.";
        } else if (text.contains("romance")) {
            return "💖 Try: La La Land.";
        } else if (text.contains("book")) {
            // try to parse id
            try {
                String[] words = text.split(" ");
                Long id = Long.parseLong(words[words.length - 1]);
                return movieService.bookMovie(id, user);
            } catch (Exception e) {
                return "❌ Please say: book movie id 2";
            }
        } else if (text.contains("view bookings")) {
            List<Booking> bookings = movieService.getAllBookings();
            if (bookings.isEmpty()) return "📝 No bookings yet!";
            StringBuilder response = new StringBuilder("📋 Your Bookings:\n");
            for (Booking b : bookings) {
                response.append("🎟️ ").append(b.getMovieTitle()).append(" (ID ").append(b.getBookingId()).append(") for ").append(b.getUsername()).append("\n");
            }
            return response.toString();
        } else {
            return "💬 Try asking: \"Suggest me a Sci-Fi movie\", \"Book movie id 2\", or \"View bookings\".";
        }
    }

    @GetMapping("/book")
    public String book(@RequestParam Long id, @RequestParam(defaultValue = "User") String user) {
        return movieService.bookMovie(id, user);
    }

    @GetMapping("/bookings")
    public List<Booking> viewBookings() {
        return movieService.getAllBookings();
    }
}
