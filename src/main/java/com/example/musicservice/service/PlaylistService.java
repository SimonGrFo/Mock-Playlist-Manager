package com.example.musicservice.service;

import com.example.musicservice.model.Playlist;
import com.example.musicservice.model.Track;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class PlaylistService {

    private final Playlist playlist = new Playlist();

    public void addTrackToPlaylist(Track track) {
        playlist.addTrack(track);
    }

}
