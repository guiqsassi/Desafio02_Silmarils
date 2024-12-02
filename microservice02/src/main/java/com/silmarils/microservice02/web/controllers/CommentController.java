package com.silmarils.microservice02.web.controllers;

import com.silmarils.microservice02.entities.Comment;
import com.silmarils.microservice02.services.CommentService;
import com.silmarils.microservice02.web.dto.CommentCreateDto;
import com.silmarils.microservice02.web.dto.CommentResponseDto;
import com.silmarils.microservice02.web.dto.CommentUpdateDto;
import com.silmarils.microservice02.web.dto.mapper.CommentMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> create(@RequestBody @Valid CommentCreateDto comment) {

    return new ResponseEntity<>(CommentMapper.toDto(commentService.create(CommentMapper.toComment(comment))), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> get(@PathVariable String id) {
        return ResponseEntity.ok(CommentMapper.toDto(commentService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAll() {
        return ResponseEntity.ok(CommentMapper.toListDto(commentService.findAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable String id, @RequestBody @Valid CommentUpdateDto comment) {
        return ResponseEntity.ok(CommentMapper.toDto(commentService.update(id, CommentMapper.toComment(comment))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> delete(@PathVariable String id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public  ResponseEntity<List<CommentResponseDto>> getByEmail(@PathVariable String email) {
     commentService.findByEmail(email);

     return ResponseEntity.ok(CommentMapper.toListDto(commentService.findByEmail(email)));
    }



}
