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
    
    @GetMapping("/upload/testTask")
    void upload() throws IOException{
       Stream<Path> fileList =Files.list(Paths.get("C:\\Users\\Julio\\Desktop\\fichiersJson"));   
        fileList.forEach(
           file->{
            MultiValueMap filesToUpload = new LinkedMultiValueMap<>(); 
            try {
                filesToUpload.add("file", new UrlResource(file.toAbsolutePath().toUri()));
                restTemplate.postForEntity(baseUrl+ "/file/upload", filesToUpload, Void.class);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
           }
       );
    }
}
