package com.silmarils.microservice02.web.controllers;

import com.silmarils.microservice02.dto.PostCreateDto;
import com.silmarils.microservice02.dto.PostResponseDto;
import com.silmarils.microservice02.dto.mapper.PostMapper;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    PostMapper postMapper;

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> post(@Valid @RequestBody PostCreateDto postCreateDto) {
        System.out.println(postCreateDto);
        Post postCreated = postService.create(postMapper.postDtoToPost(postCreateDto));
        return new ResponseEntity<>(postMapper.postToPostResponseDto(postCreated), HttpStatus.CREATED);
    }

    @RequestMapping(value ="/{id}",method = RequestMethod.GET)
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        Post post = postService.findById(String.valueOf(id));
        return  ResponseEntity.ok().body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        postService.delete(id);


        return ResponseEntity.noContent().build();
    }
}
