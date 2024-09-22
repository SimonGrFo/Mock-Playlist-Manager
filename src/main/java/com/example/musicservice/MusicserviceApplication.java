package com.example.musicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicserviceApplication.class, args);
	}
}
// Add a track into playlist: http://localhost:8080/search/track?track=
// View the tracks in the playlist http://localhost:8080/playlist