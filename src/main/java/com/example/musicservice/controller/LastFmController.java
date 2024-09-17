package com.example.musicservice.controller;

import com.example.musicservice.service.LastFmService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LastFmController {

    private final LastFmService lastFmService;

    public LastFmController(LastFmService lastFmService) {
        this.lastFmService = lastFmService;
    }

    @GetMapping("/search/album")
    public Mono<ResponseEntity<String>> searchAlbum(@RequestParam @NotBlank String album) {
        return lastFmService.searchAlbum(album)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/search/artist")
    public Mono<ResponseEntity<String>> searchArtist(@RequestParam @NotBlank String artist) {
        return lastFmService.searchArtist(artist)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/search/track")
    public Mono<ResponseEntity<String>> searchTrack(@RequestParam @NotBlank String track) {
        return lastFmService.searchTrack(track)
                .map(ResponseEntity::ok);
    }
}
