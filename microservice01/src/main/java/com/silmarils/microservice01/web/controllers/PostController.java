package com.silmarils.microservice01.web.controllers;

import com.silmarils.microservice01.dtos.PostCreateDto;
import com.silmarils.microservice01.dtos.PostResponseDto;
import com.silmarils.microservice01.feignClients.PostConsumerFeign;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostConsumerFeign postConsumerFeign;

    @GetMapping("")
    public ResponseEntity<List<PostResponseDto>> list(){
        return postConsumerFeign.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> get(@PathVariable String id){
        return postConsumerFeign.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        return postConsumerFeign.delete(id);
    }

    @PostMapping("")
    public ResponseEntity<PostResponseDto> createPost( @RequestBody PostCreateDto postCreateDto) {
        return postConsumerFeign.save(postCreateDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable String id, @RequestBody @Valid PostCreateDto post){
        return postConsumerFeign.update(id, post);
    }


}
