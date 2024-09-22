package com.example.musicservice.service;

import com.example.musicservice.model.Track;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Service
public class LastFmService {

    private static final Logger logger = LoggerFactory.getLogger(LastFmService.class);

    @Value("${lastfm.api.key}")
    private String apiKey;

    @Value("${lastfm.api.url}")
    private String lastFmUrl;

    @Value("${lastfm.api.format}")
    private String apiFormat;

    private final WebClient webClient;

    public LastFmService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(lastFmUrl).build();
    }

    private String buildUrl(String method, Map<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(lastFmUrl);

        builder.queryParam("method", method);
        params.forEach(builder::queryParam);

        builder.queryParam("api_key", apiKey)
                .queryParam("format", apiFormat);

        return builder.build().toUriString();
    }

    public Mono<String> makeApiCall(String method, Map<String, String> params) {
        String url = buildUrl(method, params);
        logger.debug("Constructed URL: {}", url);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> logger.error("Error occurred during API call: {}", e.getMessage()))
                .onErrorResume(e -> {
                    logger.warn("Error occurred while calling Last.fm API: {}", e.getMessage());
                    return Mono.just("Error with API call.");
                });
    }

    public Mono<String> searchAlbum(String albumName) {
        Map<String, String> params = Map.of("album", albumName);
        return makeApiCall("album.search", params);
    }

    public Mono<String> searchArtist(String artistName) {
        Map<String, String> params = Map.of("artist", artistName);
        return makeApiCall("artist.search", params);
    }

    public Mono<Track> searchTrack(String trackName) {
        Map<String, String> params = Map.of("track", trackName);

        return makeApiCall("track.search", params)
                .map(response -> {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject trackObject = jsonObject
                            .getJSONObject("results")
                            .getJSONObject("trackmatches")
                            .getJSONArray("track")
                            .getJSONObject(0);

                    Track track = new Track();
                    track.setName(trackObject.getString("name"));
                    track.setArtist(trackObject.getString("artist"));
                    track.setUrl(trackObject.getString("url"));

                    return track;
                });
    }
}