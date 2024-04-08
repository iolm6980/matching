package com.match.matching.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Slf4j
@Service
public class FilterService {
    private String PROFANITY_PATTERN = "";

    @PostConstruct
    public void init() throws IOException {
        PROFANITY_PATTERN =  Files.readString(Paths.get("Profanity.txt")).replaceAll("\\\"", "");
    }
    public String filterProfanity(JsonNode jsonNode){
        String message = jsonNode.get("message").asText();
        Pattern pattern = Pattern.compile(PROFANITY_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        String modify = matcher.replaceAll("**");
        return modify;
    }
}
