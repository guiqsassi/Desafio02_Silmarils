package com.silmarils.microservice01.web;

import com.silmarils.microservice01.Microservice01Application;
import com.silmarils.microservice01.dtos.PostCreateDto;
import com.silmarils.microservice01.dtos.PostResponseDto;
import com.silmarils.microservice01.feignClients.PostConsumerFeign;
import feign.Response;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Microservice01Application.class)
public class PostIt {

    @Autowired
    private PostConsumerFeign postConsumerFeign;


    @Test
    public void postCreate_WithValidData_ExpectStatus201(){
        PostCreateDto postCreated = new PostCreateDto(23, "Titulo", "Bom dia pessoal");
        ResponseEntity<PostResponseDto> res = postConsumerFeign.save
                (postCreated);
        PostResponseDto dto =res.getBody();

        Assertions.assertEquals(dto.getBody(), postCreated.getBody());
        Assertions.assertEquals(dto.getTitle(), postCreated.getTitle());
        Assertions.assertNotNull(dto.getId());
        Assertions.assertEquals(res.getStatusCode(), HttpStatusCode.valueOf(201));

        postConsumerFeign.delete(dto.getId());

    }

    @Test
    public void getPostById_WithValidId_ExpectStatus200(){
        PostCreateDto postCreate = new PostCreateDto(23, "Titulo", "Bom dia pessoal");
        ResponseEntity<PostResponseDto> postCreated = postConsumerFeign.save
                (postCreate);

        ResponseEntity<PostResponseDto> read = postConsumerFeign.getById(postCreated.getBody().getId());
        Assertions.assertNotNull(read.getBody());
        Assertions.assertEquals(read.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals(read.getBody().getId(), postCreated.getBody().getId());
        Assertions.assertEquals(read.getBody().getTitle(), postCreated.getBody().getTitle());
        Assertions.assertEquals(read.getBody().getBody(), postCreated.getBody().getBody());

        postConsumerFeign.delete(read.getBody().getId());


    }

    @Test
    public void DeletePostById_WithValidId_ExpectStatus204(){
        PostCreateDto postCreate = new PostCreateDto(23, "Titulo", "Bom dia pessoal");
        ResponseEntity<PostResponseDto> postCreated = postConsumerFeign.save
                (postCreate);
       ResponseEntity res = postConsumerFeign.delete(postCreated.getBody().getId());

       Assertions.assertEquals(res.getStatusCode(), HttpStatusCode.valueOf(204));

    }

    @Test
    public void list_ExpectStatus200WithListOfPosts(){
        ResponseEntity<List<PostResponseDto>> res = postConsumerFeign.list();

        Assertions.assertEquals(res.getStatusCode(), HttpStatusCode.valueOf(200));

        Assertions.assertNotNull(res.getBody());


    }
    @Test
    public void updatePost_WithValidId_ExpectStatus200(){
        PostCreateDto postCreated = new PostCreateDto(23, "Titulo", "Bom dia pessoal");
        ResponseEntity<PostResponseDto> res = postConsumerFeign.save(postCreated);
        PostResponseDto dto =res.getBody();

        PostCreateDto postUpdate = new PostCreateDto(23, "Titulo Atualizado", "Bom tarde pessoal");
        ResponseEntity<PostResponseDto> response = postConsumerFeign.update(dto.getId() ,postUpdate);

        Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(204));

        postConsumerFeign.delete(dto.getId());
    }


}
