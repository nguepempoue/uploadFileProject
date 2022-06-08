package com.example.projectFile.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("file")
public class TestTask {

    @Autowired
    RestTemplate restTemplate;

    String baseUrl = "http://localhost:8080";
    int time = 0;
    int count = 0;
    @GetMapping("/upload/testTask")
     public ResponseEntity<String> upload() throws IOException{
       Stream<Path> fileList = Files.list(Paths.get("C:\\Users\\Julio\\Desktop\\fichiersJson"));   
        fileList.forEach(
           file->{
            MultiValueMap<String, Object> filesToUpload = new LinkedMultiValueMap<>(); 
            try {
                filesToUpload.add("file", new UrlResource(file.toAbsolutePath().toUri()));
                long startTime = System.currentTimeMillis();
                restTemplate.postForEntity(baseUrl+ "/file/upload", filesToUpload, Void.class);
                long stopTime = System.currentTimeMillis(); 
                long elapsedTime = stopTime - startTime;
                time = (int) (time + elapsedTime);  
                count = count + 1; 
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
           }
       );
       fileList.close();
       return new ResponseEntity<>("Avarage time: " + time/count + " ms", HttpStatus.OK);
    }
}
