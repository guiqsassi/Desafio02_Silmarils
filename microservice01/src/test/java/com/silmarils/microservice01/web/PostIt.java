package com.silmarils.microservice01.web;

import com.silmarils.microservice01.Microservice01Application;
import com.silmarils.microservice01.dtos.PostCreateDto;
import com.silmarils.microservice01.dtos.PostResponseDto;
import com.silmarils.microservice01.dtos.PostUpdateDto;
import com.silmarils.microservice01.exceptions.BadRequestException;
import com.silmarils.microservice01.exceptions.EntityNotFoundException;
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
    public void postCreate_WithInvalidData_ThrowBadRequestException(){
        PostCreateDto postCreated = new PostCreateDto(23, "ti", "Bom dia pessoal");
        Assertions.assertThrows(BadRequestException.class, () -> {postConsumerFeign.save(postCreated);});

        PostCreateDto postCreated2 = new PostCreateDto(23, "titulo", "Bom");
        Assertions.assertThrows(BadRequestException.class, () -> {postConsumerFeign.save(postCreated2);});

        PostCreateDto postCreated3 = new PostCreateDto(null, "titulo", "Bom Dia pessoal");
        Assertions.assertThrows(BadRequestException.class, () -> {postConsumerFeign.save(postCreated3);});

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
    public void getPostById_WithNonExistentId_ThrowEntityNotFoundException(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> { postConsumerFeign.getById("TesteId01");});

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
    public void DeletePostById_WithNonExistentId_ThrowEntityNotFoundException(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> { postConsumerFeign.delete("TesteId01");});
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

        PostUpdateDto postUpdate = new PostUpdateDto("Titulo Atualizado", "Bom tarde pessoal");
        ResponseEntity<String> response = postConsumerFeign.update(dto.getId() ,postUpdate);

        Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));

        postConsumerFeign.delete(dto.getId());
    }

    @Test
    public void updatePost_WithInvalidId_ThrowEntityNotFoundException(){

        PostUpdateDto postUpdate = new PostUpdateDto("Titulo Atualizado", "Bom tarde pessoal");
        Assertions.assertThrows(EntityNotFoundException.class, () -> { postConsumerFeign.update("TesteId01", postUpdate);});

    }

    @Test
    public void updatePost_WithInvalidData_ExpectStatus400(){
        PostCreateDto postCreated = new PostCreateDto(23, "Titulo", "Bom dia pessoal");
        ResponseEntity<PostResponseDto> res = postConsumerFeign.save(postCreated);
        PostResponseDto dto =res.getBody();

        PostUpdateDto postUpdate = new PostUpdateDto("Tit", "Bom tarde pessoal");
        Assertions.assertThrows(BadRequestException.class, () -> { postConsumerFeign.update(dto.getId(), postUpdate);});

        PostUpdateDto postUpdate2 = new PostUpdateDto("Titutlo atualizado", "Bom");
        Assertions.assertThrows(BadRequestException.class, () -> { postConsumerFeign.update(dto.getId(), postUpdate2);});

        postConsumerFeign.delete(dto.getId());
    }


}
