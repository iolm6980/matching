package com.match.matching.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
@Service
public class FilterService {
    private final String ROOT_PATH = File.separator + "var" + File.separator + "jenkins_home"+ File.separator +"workspace"+ File.separator +"match"+ File.separator;
    private String PROFANITY_PATTERN = "";

    @PostConstruct
    public void init() throws IOException {
        File currentFolder = new File(".");
        File[] fileList = currentFolder.listFiles();

        // 파일 목록 출력
        for (File file : fileList) {
            System.out.println(file.getName());
        }
//        try{
//            PROFANITY_PATTERN =  Files.readString(Paths.get(ROOT_PATH + "Profanity.txt")).replaceAll("\\\"", "");
//        } catch (BeanCreationException e){
//            Path path = Paths.get(ROOT_PATH + "test.txt");
//            Files.write(path, "123".getBytes());
//            System.out.println(e);
//        }
    }
    public String filterProfanity(JsonNode jsonNode){
        String message = jsonNode.get("message").asText();
        Pattern pattern = Pattern.compile(PROFANITY_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        String modify = matcher.replaceAll("**");
        return modify;
    }
}
