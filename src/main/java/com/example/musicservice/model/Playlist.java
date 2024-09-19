package com.example.musicservice.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Playlist {
    private List<Track> tracks = new ArrayList<>();

    public void addTrack(Track track) {
        this.tracks.add(track);
    }
}
