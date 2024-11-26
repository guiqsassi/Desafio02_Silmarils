package com.silmarils.microservice02.web.controllers;

import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> post(@RequestBody Post post) {
        Post postCreated = postService.create(post);

        return new ResponseEntity<>(postCreated, HttpStatus.CREATED);

    }
}
