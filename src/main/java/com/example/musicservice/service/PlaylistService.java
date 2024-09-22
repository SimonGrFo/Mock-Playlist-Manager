package com.example.musicservice.service;

import com.example.musicservice.model.Track;
import com.example.musicservice.repository.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final TrackRepository trackRepository;

    public PlaylistService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public void addTrackToPlaylist(Track track) {
        trackRepository.save(track);
    }

    public List<Track> getPlaylist() {
        return trackRepository.findAll();
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }
}
