package com.silmarils.microservice02.web.controllers;

import com.silmarils.microservice02.web.dto.PostCreateDto;
import com.silmarils.microservice02.web.dto.PostResponseDto;
import com.silmarils.microservice02.web.dto.PostUpdateDto;
import com.silmarils.microservice02.web.dto.mapper.PostMapper;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> post(@Valid @RequestBody PostCreateDto postCreateDto) {
        System.out.println(postCreateDto);
        Post postCreated = postService.create(PostMapper.postDtoToPost(postCreateDto));
        return new ResponseEntity<>(PostMapper.postToPostResponseDto(postCreated), HttpStatus.CREATED);
    }

    @RequestMapping(value ="/{id}",method = RequestMethod.GET)
    public ResponseEntity<PostResponseDto> getPost(@PathVariable String id) {
        Post post = postService.findById(String.valueOf(id));
        return  ResponseEntity.ok().body(PostMapper.postToPostResponseDto(post));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        postService.delete(id);

        return ResponseEntity.noContent().build();
    }
    @RequestMapping(value ="/{id}",method = RequestMethod.PUT)
    public ResponseEntity<PostResponseDto> Update (@PathVariable String id, @RequestBody @Valid PostUpdateDto postupdatedto) {
        Post post = postService.create(PostMapper.postUpdateDtoToPost(postupdatedto));

        return  ResponseEntity.ok(PostMapper.postToPostResponseDto(postService.update(post, id)));
    }

    @GetMapping()
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<Post> posts = postService.findAll();

        return ResponseEntity.ok(PostMapper.postToPostResponseDtoList(posts));
    }

}
