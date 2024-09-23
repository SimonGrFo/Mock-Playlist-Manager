package com.example.musicservice.service;

import com.example.musicservice.model.Track;
import com.example.musicservice.repository.TrackRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    private final TrackRepository trackRepository;

    public PlaylistService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Track addTrackToPlaylist(Track track) {
        return trackRepository.save(track);
    }

    public List<Track> getPlaylist() {
        return trackRepository.findAll();
    }

    public Optional<Track> updateTrack(Long id, Track updatedTrack) {
        return trackRepository.findById(id).map(existingTrack -> {
            existingTrack.setName(updatedTrack.getName());
            existingTrack.setArtist(updatedTrack.getArtist());
            existingTrack.setUrl(updatedTrack.getUrl());
            return trackRepository.save(existingTrack);
        });
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }

    public Optional<Track> findTrackById(Long id) {
        return trackRepository.findById(id);
    }
}