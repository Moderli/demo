package com.example.demo.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Comic;
import com.example.demo.model.ComicResponse;
import com.example.demo.model.GiphyResponse;
import com.example.demo.model.Sticker;

@Controller
public class HomeController {

    @Value("${marvel.api.key}")
    private String marvelApiKey;

    @Value("${marvel.api.privateKey}")
    private String marvelPrivateKey; 

    @Value("${giphy.api.key}")
    private String giphyApiKey; 

    private static final String MARVEL_API_URL = "https://gateway.marvel.com/v1/public/comics";

    private static final String GIPHY_API_URL = "https://api.giphy.com/v1/stickers/search";

    private final RestTemplate restTemplate;

    public HomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Home page showing comics, with optional search functionality
    @GetMapping("/home")
    public String home(@RequestParam(value = "search", required = false) String search, Model model) throws NoSuchAlgorithmException {
        String ts = String.valueOf(System.currentTimeMillis()); // Timestamp
        String hash = generateHash(ts); // MD5 Hash

        // Construct the Marvel API URL
        StringBuilder urlBuilder = new StringBuilder(MARVEL_API_URL)
            .append("?apikey=").append(marvelApiKey)
            .append("&ts=").append(ts)
            .append("&hash=").append(hash);
        
        if (search != null && !search.isEmpty()) {
            urlBuilder.append("&titleStartsWith=").append(search); 
        }
        String url = urlBuilder.toString();

        // Fetch comics from Marvel API
        ResponseEntity<ComicResponse> responseEntity = restTemplate.getForEntity(url, ComicResponse.class);
        ComicResponse response = responseEntity.getBody();

        if (response != null && response.getData() != null) {
            List<Comic> comics = response.getData().getResults();
            comics.forEach(this::populateComicUrls);
            model.addAttribute("comics", comics);
        }

        model.addAttribute("search", search);
        return "home";
    }
    @GetMapping("/stickers")
    public String stickers(@RequestParam(value = "search", required = false) String search, Model model) {
        String searchTerm = (search != null && !search.isEmpty()) ? search : "Marvel";
        String url = GIPHY_API_URL + "?api_key=" + giphyApiKey + "&q=" + searchTerm + "&limit=10";
    
        ResponseEntity<GiphyResponse> responseEntity = restTemplate.getForEntity(url, GiphyResponse.class);
        GiphyResponse response = responseEntity.getBody();
    
        if (response != null && response.getData() != null) {
            List<Sticker> stickers = new ArrayList<>();
            response.getData().forEach(stickerData -> {
                String imageUrl = stickerData.getImages().getOriginal().getUrl();
                stickers.add(new Sticker(imageUrl));
            });
            model.addAttribute("stickers", stickers);
        }
    
        model.addAttribute("search", search);
        return "stickers";
    }
    
    private String generateHash(String ts) throws NoSuchAlgorithmException {
        String toHash = ts + marvelPrivateKey + marvelApiKey;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(toHash.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void populateComicUrls(Comic comic) {
        if (comic.getThumbnail() != null) {
            String path = comic.getThumbnail().getPath();
            String extension = comic.getThumbnail().getExtension();
            comic.setImageUrl(path, extension); 
        }
        if (comic.getUrls() != null) {
            comic.getUrls().stream()
                .filter(urlObj -> "comiclink".equals(urlObj.getType()))
                .findFirst()
                .ifPresent(urlObj -> comic.setComiclink(urlObj.getUrl()));
        }
    }
}
