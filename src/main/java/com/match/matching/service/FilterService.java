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
        PROFANITY_PATTERN =  Files.readString(Paths.get("/var/jenkins_home/workspace/match/Profanity")).replaceAll("\\\"", "");
    }
    public String filterProfanity(JsonNode jsonNode){
        String message = jsonNode.get("message").asText();
        Pattern pattern = Pattern.compile(PROFANITY_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        String modify = matcher.replaceAll("**");
        return modify;
    }
}
