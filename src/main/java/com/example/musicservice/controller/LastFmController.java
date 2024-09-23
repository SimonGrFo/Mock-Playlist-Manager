package com.example.musicservice.controller;

import com.example.musicservice.service.LastFmService;
import com.example.musicservice.service.PlaylistService;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

    @RestController
    public class LastFmController {

        private static final Logger logger = LoggerFactory.getLogger(LastFmController.class);

        private final LastFmService lastFmService;
        private final PlaylistService playlistService;

        public LastFmController(LastFmService lastFmService, PlaylistService playlistService) {
            this.lastFmService = lastFmService;
            this.playlistService = playlistService;
        }

        @GetMapping("/search/track")
        public Mono<ResponseEntity<String>> searchTrack(@RequestParam @NotBlank String track) {
            logger.info("Searching for track: {}", track);

            return lastFmService.searchTrack(track)
                    .map(foundTrack -> {
                        playlistService.addTrackToPlaylist(foundTrack);
                        logger.info("Track added to playlist: {} by {}", foundTrack.getName(), foundTrack.getArtist());
                        return ResponseEntity.ok("Track added to playlist: " + foundTrack.getName() + " by " + foundTrack.getArtist());
                    })
                    .onErrorResume(e -> {
                        logger.error("Error during track search: {}", e.getMessage());
                        return Mono.just(ResponseEntity.badRequest().body("Failed to search track: " + track));
                    });
        }

        @GetMapping("/playlist")
        public ResponseEntity<?> getPlaylist() {
            logger.info("Fetching the playlist");
            return ResponseEntity.ok(playlistService.getPlaylist());
        }
    }

