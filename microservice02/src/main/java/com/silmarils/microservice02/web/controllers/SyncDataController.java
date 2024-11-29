package com.silmarils.microservice02.web.controllers;

import com.silmarils.microservice02.web.dto.PostResponseDto;
import com.silmarils.microservice02.web.dto.mapper.PostMapper;
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
@RequestMapping("/api/sync-data")
public class SyncDataController {
    @Autowired
    PostSyncService postSyncService;


    @PostMapping
    public ResponseEntity<List<PostResponseDto>> syncData() {

        List<PostResponseDto> responseDtos = PostMapper.postToPostResponseDtoList(postSyncService.syncData());


        return new ResponseEntity<>(responseDtos, HttpStatus.CREATED);
    }
}
