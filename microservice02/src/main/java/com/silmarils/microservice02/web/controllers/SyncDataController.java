package com.silmarils.microservice02.web.controllers;

import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.services.PostSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sync-data")
public class SyncDataController {
    @Autowired
    PostSyncService postSyncService;

    @PostMapping
    public ResponseEntity<List<Post>> syncData() {
        return new ResponseEntity<>(postSyncService.syncData(), HttpStatus.CREATED);
    }
}