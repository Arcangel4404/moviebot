package com.parinay.moviebot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private Long bookingId;
    private Long movieId;
    private String movieTitle;
    private String username;
}
