package com.silmarils.microservice01.web.controllers;

import com.silmarils.microservice01.dtos.CommentCreateDto;
import com.silmarils.microservice01.dtos.CommentResponseDto;
import com.silmarils.microservice01.feignClients.CommentConsumerFeing;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentsController {

    @Autowired
    CommentConsumerFeing commentConsumerFeing;

    @PostMapping
    ResponseEntity<CommentResponseDto> create (@RequestBody CommentCreateDto commentCreateDto) {
        return commentConsumerFeing.save(commentCreateDto);
    }

    @GetMapping
    ResponseEntity<List<CommentResponseDto>> getAllComments() {return commentConsumerFeing.getAllComments();}

}
