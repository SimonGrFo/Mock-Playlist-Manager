package com.example.musicservice.controller;

import com.example.musicservice.service.LastFmService;
import com.example.musicservice.service.PlaylistService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LastFmController {

    private final LastFmService lastFmService;
    private final PlaylistService playlistService;

    public LastFmController(LastFmService lastFmService, PlaylistService playlistService) {
        this.lastFmService = lastFmService;
        this.playlistService = playlistService;
    }

    @GetMapping("/search/track")
    public Mono<ResponseEntity<String>> searchTrack(@RequestParam @NotBlank String track) {
        return lastFmService.searchTrack(track)
                .map(foundTrack -> {
                    playlistService.addTrackToPlaylist(foundTrack);  // Save the track in the playlist
                    return ResponseEntity.ok("Track added to playlist: " + foundTrack.getName());
                });
    }

    @GetMapping("/playlist")
    public ResponseEntity<?> getPlaylist() {
        return ResponseEntity.ok(playlistService.getPlaylist());
    }
}
