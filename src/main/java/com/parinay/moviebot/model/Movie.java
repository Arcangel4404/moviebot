package com.parinay.moviebot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Movie {
    private Long id;
    private String title;
    private String genre;
    private int duration; // in minutes
}
