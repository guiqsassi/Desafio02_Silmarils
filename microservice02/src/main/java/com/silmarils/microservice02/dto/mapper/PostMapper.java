package com.silmarils.microservice02.dto.mapper;

import com.silmarils.microservice02.dto.PostCreateDto;
import com.silmarils.microservice02.dto.PostResponseDto;
import com.silmarils.microservice02.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

   Post postDtoToPost(PostCreateDto postCreateDto);

   PostResponseDto postToPostResponseDto (Post post);

}
