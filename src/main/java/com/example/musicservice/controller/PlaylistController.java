package com.example.musicservice.controller;

import com.example.musicservice.model.Track;
import com.example.musicservice.service.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ModifyPlaylist")
public class PlaylistController {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class);
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<List<Track>> getAllTracks() {
        logger.info("Fetching all tracks from the playlist");
        return ResponseEntity.ok(playlistService.getPlaylist());
    }

    @PostMapping
    public ResponseEntity<Track> addTrack(@RequestBody Track track) {
        logger.info("Adding track '{}' by '{}' to the playlist", track.getName(), track.getArtist());
        return ResponseEntity.ok(playlistService.addTrackToPlaylist(track));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable Long id, @RequestBody Track updatedTrack) {
        logger.info("Updating track with id: {}", id);
        return playlistService.updateTrack(id, updatedTrack)
                .map(updated -> {
                    logger.info("Updated track with id: {}", id);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    logger.warn("Track with id '{}' not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTrack(@PathVariable Long id) {
        return playlistService.findTrackById(id)
                .map(track -> {
                    playlistService.deleteTrack(id);
                    logger.info("Deleted track with id: {}", id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> {
                    logger.warn("Track with id '{}' not found", id);
                    return ResponseEntity.notFound().build();
                });
    }
}