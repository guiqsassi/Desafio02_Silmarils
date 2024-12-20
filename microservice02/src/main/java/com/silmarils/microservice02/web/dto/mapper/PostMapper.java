package com.silmarils.microservice02.web.dto.mapper;

import com.silmarils.microservice02.web.dto.PostCreateDto;
import com.silmarils.microservice02.web.dto.PostResponseDto;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.web.dto.PostUpdateDto;
import org.modelmapper.ModelMapper;

import java.util.List;

public class PostMapper {


    public static Post postDtoToPost(PostCreateDto dto){
        Post post = new Post();
        post.setUserId(dto.getUserId());
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        return post;
    }

    public static PostResponseDto postToPostResponseDto(Post post){
        return new ModelMapper().map(post, PostResponseDto.class);
    }

    public static List<PostResponseDto> postToPostResponseDtoList(List<Post> posts){
        return posts.stream().map(PostMapper::postToPostResponseDto).toList();
    }

    public static Post postUpdateDtoToPost(PostUpdateDto dto){
        return new ModelMapper().map(dto, Post.class);

    }

}
