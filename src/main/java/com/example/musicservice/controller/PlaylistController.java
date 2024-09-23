package com.example.musicservice.controller;

import com.example.musicservice.model.Track;
import com.example.musicservice.service.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        List<Track> tracks = playlistService.getPlaylist();
        return ResponseEntity.ok(tracks);
    }

    @PostMapping
    public ResponseEntity<Track> addTrack(@RequestBody Track track) {
        logger.info("Adding track to the playlist: {} by {}", track.getName(), track.getArtist());
        Track savedTrack = playlistService.addTrackToPlaylist(track);
        return ResponseEntity.ok(savedTrack);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable Long id, @RequestBody Track updatedTrack) {
        logger.info("Updating track with id: {}", id);
        Optional<Track> updated = playlistService.updateTrack(id, updatedTrack);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
        logger.info("Deleting track with id: {}", id);
        Optional<Track> track = playlistService.findTrackById(id);
        if (track.isPresent()) {
            playlistService.deleteTrack(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
