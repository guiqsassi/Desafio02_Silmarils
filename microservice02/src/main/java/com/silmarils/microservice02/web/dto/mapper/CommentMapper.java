package com.silmarils.microservice02.web.dto.mapper;


import com.silmarils.microservice02.entities.Comment;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.web.dto.CommentCreateDto;
import com.silmarils.microservice02.web.dto.CommentResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;

public class CommentMapper {

    public static Comment toComment(CommentCreateDto dto){
        Comment comment = new Comment();
        comment.setName(dto.getName());
        comment.setBody(dto.getBody());
        comment.setEmail(dto.getEmail());
        Post post = new Post();
        post.setId(dto.getPostId());
        comment.setPostId(post);
        return comment;
    }
    public static CommentResponseDto toDto(Comment comment){
        return new ModelMapper().map(comment, CommentResponseDto.class);
    }

    public static List<CommentResponseDto> toDto(List<Comment> comment){

        return comment.stream().map(CommentMapper::toDto).toList();
    }
}
