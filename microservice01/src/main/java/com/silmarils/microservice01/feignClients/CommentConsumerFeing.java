package com.silmarils.microservice01.feignClients;

import com.silmarils.microservice01.config.MyFeignClientConfiguration;
import com.silmarils.microservice01.dtos.CommentCreateDto;
import com.silmarils.microservice01.dtos.CommentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "comment-consumer", url = "http://localhost:8081/api/comments", configuration = MyFeignClientConfiguration.class)
public interface CommentConsumerFeing {

    @GetMapping("")
    public ResponseEntity<List<CommentResponseDto>> getAllComments();

    @GetMapping("")
    public ResponseEntity<CommentResponseDto> save(CommentCreateDto commentCreateDto);


    







}
