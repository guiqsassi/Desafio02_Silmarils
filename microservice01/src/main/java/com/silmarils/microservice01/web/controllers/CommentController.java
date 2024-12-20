package com.silmarils.microservice01.web.controllers;

import com.silmarils.microservice01.dtos.CommentCreateDto;
import com.silmarils.microservice01.dtos.CommentResponseDto;
import com.silmarils.microservice01.dtos.CommentUpdateDto;
import com.silmarils.microservice01.feignClients.CommentConsumerFeing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    public CommentConsumerFeing commentConsumerFeing;

    @PostMapping
    public ResponseEntity<CommentResponseDto> create (@RequestBody CommentCreateDto commentCreateDto) {
        return commentConsumerFeing.save(commentCreateDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<CommentResponseDto> update (@PathVariable("id") String id, @RequestBody CommentUpdateDto commentUpdateDto) {
        return commentConsumerFeing.update(id, commentUpdateDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {return commentConsumerFeing.getAllComments();}

    @GetMapping("{id}")
    public  ResponseEntity<CommentResponseDto> getCommentById(@PathVariable String id) { return commentConsumerFeing.getCommentById(id);}

    @GetMapping("/post/{id}")
    public  ResponseEntity<List<CommentResponseDto>> getCommentByPostId(@PathVariable String id) { return commentConsumerFeing.getCommentByPostId(id);}

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) { return commentConsumerFeing.delete(id);}

    @GetMapping("/email/{email}")
    public ResponseEntity<List<CommentResponseDto>> getByEmail(@PathVariable String email) { return commentConsumerFeing.getByEmail(email);}


}
