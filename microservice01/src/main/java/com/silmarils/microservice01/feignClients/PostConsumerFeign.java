package com.silmarils.microservice01.feignClients;

import com.silmarils.microservice01.dtos.PostCreateDto;
import com.silmarils.microservice01.dtos.PostResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "post-consumer", url = "http://localhost:8081/api/posts")
public interface PostConsumerFeign {
    @GetMapping("")
    public ResponseEntity<List<PostResponseDto>> list();

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getById(@PathVariable("id") String id);

    @PostMapping("")
    public ResponseEntity<PostResponseDto> save(@RequestBody PostCreateDto post);

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable("id") String id, @RequestBody PostCreateDto post);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id);
}
