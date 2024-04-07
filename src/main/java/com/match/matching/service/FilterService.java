package com.match.matching.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class FilterService {
    private String PROFANITY_PATTERN;

    @PostConstruct
    public void init() throws IOException {
        PROFANITY_PATTERN =  Files.readString(Paths.get("Profanity")).replaceAll("\\\\", "");

    }
    public String filterProfanity(JsonNode jsonNode){
        System.out.println("패턴" + PROFANITY_PATTERN);
        String message = jsonNode.get("message").asText();
        String test = "ㅅㅂ";
        Pattern pattern = Pattern.compile(PROFANITY_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(test);
        String modify = matcher.replaceAll("**");
        System.out.println(modify);
        return modify;
    }
}
