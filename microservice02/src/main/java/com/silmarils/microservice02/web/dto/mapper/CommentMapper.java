package com.silmarils.microservice02.web.dto.mapper;


import com.silmarils.microservice02.entities.Comment;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.web.dto.CommentCreateDto;
import com.silmarils.microservice02.web.dto.CommentFeignDto;
import com.silmarils.microservice02.web.dto.CommentResponseDto;
import com.silmarils.microservice02.web.dto.CommentUpdateDto;
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

    public static List<CommentResponseDto> toListDto(List<Comment> comment){

        return comment.stream().map(CommentMapper::toDto).toList();
    }

    public static List<Comment> toListComment(List<CommentFeignDto> comment){

        return comment.stream().map(CommentMapper::toComment).toList();
    }

    public static Comment toComment(CommentUpdateDto updateDto){
        return new ModelMapper().map(updateDto, Comment.class);
    }

    public static Comment toComment(CommentFeignDto dto){
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setName(dto.getName());
        comment.setBody(dto.getBody());
        comment.setEmail(dto.getEmail());

        Post post = new Post();
        post.setId(dto.getPostId());
        comment.setPostId(post);
        return comment;
    }
}
