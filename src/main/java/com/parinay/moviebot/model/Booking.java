package com.parinay.moviebot.model;

public class Booking {
    private Long bookingId;
    private Long movieId;
    private String movieTitle;
    private String username;

    public Booking(Long bookingId, Long movieId, String movieTitle, String username) {
        this.bookingId = bookingId;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.username = username;
    }

    public Long getBookingId() { return bookingId; }
    public Long getMovieId() { return movieId; }
    public String getMovieTitle() { return movieTitle; }
    public String getUsername() { return username; }

    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public void setUsername(String username) { this.username = username; }
}
