package com.example.tempTrackService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;


@RestController
public class GenController {
    @Autowired
    OAuth2RestTemplate oauth2RestTemplate;
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/temp")
    public ResponseEntity<String> tempHandler(@RequestParam String q,@RequestParam String appid) throws JsonProcessingException {

        String uri = "https://api.openweathermap.org/data/2.5/weather?q={q}&appid={appid}";
        ResponseEntity<String> res = restTemplate.getForEntity(uri, String.class,q,appid);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = objectMapper.readValue(res.getBody(), Map.class);
        LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) map.get("main");
        float f = Float.parseFloat(linkedHashMap.get("temp").toString());
        System.out.println(f);

        String apiUrl = "https://api.spotify.com/v1/tracks/6rqhFgbbKwnb9MLmUQDhG6";
        ResponseEntity<String> responseEntity= oauth2RestTemplate.getForEntity(apiUrl, String.class);
        if(f>280) {
             responseEntity= oauth2RestTemplate.getForEntity("https://api.spotify.com/v1/search?q=party&type=track&include_external=true", String.class);
             //responseEntity = genController.trackHandler("");
        }
        else{
             responseEntity= oauth2RestTemplate.getForEntity("https://api.spotify.com/v1/search?q=rap&type=track&include_external=true", String.class);
             //responseEntity=genController.trackHandler();
        }
        return responseEntity;
    }

}
