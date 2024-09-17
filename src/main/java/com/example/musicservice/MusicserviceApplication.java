package com.example.musicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicserviceApplication.class, args);
	}

}
// Search for an album: http://localhost:8080/search/album?album=
// Search for an artist: http://localhost:8080/search/artist?artist=
// Search for a track: http://localhost:8080/search/track?track=
