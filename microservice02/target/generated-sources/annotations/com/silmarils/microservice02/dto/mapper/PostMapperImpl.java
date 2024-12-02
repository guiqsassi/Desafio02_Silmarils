package com.silmarils.microservice02.dto.mapper;

import com.silmarils.microservice02.dto.PostCreateDto;
import com.silmarils.microservice02.dto.PostResponseDto;
import com.silmarils.microservice02.entities.Post;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-29T17:25:26-0300",
    comments = "version: 1.6.0.Beta2, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post postDtoToPost(PostCreateDto postCreateDto) {
        if ( postCreateDto == null ) {
            return null;
        }

        Post post = new Post();

        return post;
    }

    @Override
    public PostResponseDto postToPostResponseDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponseDto postResponseDto = new PostResponseDto();

        return postResponseDto;
    }

    @Override
    public List<PostResponseDto> postToPostResponseDtoList(List<Post> posts) {
        if ( posts == null ) {
            return null;
        }

        List<PostResponseDto> list = new ArrayList<PostResponseDto>( posts.size() );
        for ( Post post : posts ) {
            list.add( postToPostResponseDto( post ) );
        }

        return list;
    }
}
