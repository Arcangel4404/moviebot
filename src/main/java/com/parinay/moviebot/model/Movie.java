package com.parinay.moviebot.model;

public class Movie {
    private Long id;
    private String title;
    private String genre;
    private int duration; // in minutes

    public Movie(Long id, String title, String genre, int duration) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getDuration() { return duration; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDuration(int duration) { this.duration = duration; }
}
