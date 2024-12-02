package com.silmarils.microservice01.feignClients;

import com.silmarils.microservice01.config.MyFeignClientConfiguration;
import com.silmarils.microservice01.dtos.CommentCreateDto;
import com.silmarils.microservice01.dtos.CommentResponseDto;
import com.silmarils.microservice01.dtos.CommentUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "comment-consumer", url = "http://localhost:8081/api/comments", configuration = MyFeignClientConfiguration.class)
public interface CommentConsumerFeing {

    @GetMapping("")
    public ResponseEntity<List<CommentResponseDto>> getAllComments();

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable("id") String id);

    @PostMapping("")
    public ResponseEntity<CommentResponseDto> save(CommentCreateDto commentCreateDto);

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable("id") String id, CommentUpdateDto commentUpdateDto);

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete(@PathVariable("id") String id);









}
