package com.silmarils.microservice02.web.controllers;

import com.silmarils.microservice02.dto.PostResponseDto;
import com.silmarils.microservice02.dto.mapper.PostMapper;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.services.PostSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sync-data")
public class SyncDataController {
    @Autowired
    PostSyncService postSyncService;

    @Autowired
    PostMapper postMapper;

    @PostMapping
    public ResponseEntity<List<PostResponseDto>> syncData() {

        List<PostResponseDto> responseDtos = postSyncService.syncData()
                .stream()
                .map(postMapper :: postToPostResponseDto)
                .collect(Collectors.toList());


        return new ResponseEntity<>(responseDtos, HttpStatus.CREATED);
    }
}
