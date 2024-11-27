package com.silmarils.microservice02.web.controllers;

import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value ="/{id}",method = RequestMethod.GET)
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        Post post = postService.findById(String.valueOf(id));
        return  ResponseEntity.ok().body(post);
    }

}
