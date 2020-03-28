package com.example.spartan.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.OffsetDateTime;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class TimeController {

    long hours=0;
    long mins=0;

    @GetMapping("/admin/time")
    public OffsetDateTime getTime() {
        return OffsetDateTime.now(Clock.systemUTC()).plusHours(this.hours).plusMinutes(this.mins);
    }
}
