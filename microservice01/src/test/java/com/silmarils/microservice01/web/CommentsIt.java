package com.silmarils.microservice01.web;

import com.silmarils.microservice01.Microservice01Application;
import com.silmarils.microservice01.dtos.*;
import com.silmarils.microservice01.exceptions.BadRequestException;
import com.silmarils.microservice01.exceptions.EntityNotFoundException;
import com.silmarils.microservice01.feignClients.CommentConsumerFeing;
import com.silmarils.microservice01.feignClients.PostConsumerFeign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Microservice01Application.class)
public class CommentsIt {

    @Autowired
    CommentConsumerFeing commentConsumerFeing;

    @Autowired
    PostConsumerFeign postConsumerFeign;

    @Test
    public void CommentCreate_WithValidData_ExpectStatus201(){
        CommentCreateDto commentCreateDto = new CommentCreateDto("test@gmail.com", "nicename", "paster", "28");
        ResponseEntity<CommentResponseDto> res = commentConsumerFeing.save(commentCreateDto);
        CommentResponseDto dto = res.getBody();

        Assertions.assertEquals(dto.getBody(), commentCreateDto.getBody());
        Assertions.assertEquals(dto.getName(), commentCreateDto.getName());
        Assertions.assertNotNull(dto.getId());
        Assertions.assertEquals(res.getStatusCode(), HttpStatusCode.valueOf(201));

        commentConsumerFeing.delete(dto.getId());
    }



    @Test
    public void CommentCreate_WithInvalidData_ExpectStatusBadRequestException(){
        CommentCreateDto commentCreateDto1 = new CommentCreateDto("te", "tentativa", "21211", "28");
        CommentCreateDto commentCreateDto2 = new CommentCreateDto("teste", "ten", "21211", "28");
        CommentCreateDto commentCreateDto3 = new CommentCreateDto("teste", "tentativa", "2", "28");
        CommentCreateDto commentCreateDto4 = new CommentCreateDto("teste", "tentativa", "2", "0");

        assertThrows(BadRequestException.class, () -> {commentConsumerFeing.save(commentCreateDto1);});
        assertThrows(BadRequestException.class, () -> {commentConsumerFeing.save(commentCreateDto2);});
        assertThrows(BadRequestException.class, () -> {commentConsumerFeing.save(commentCreateDto3);});
        assertThrows(BadRequestException.class, () -> {commentConsumerFeing.save(commentCreateDto4);});


    }

    @Test
    public void CommentUpdate_WithValidData_ExpectStatus201(){
        CommentCreateDto commentCreateDto = new CommentCreateDto("test@gmail.com", "nicename", "paster", "28");
        ResponseEntity<CommentResponseDto> res = commentConsumerFeing.save(commentCreateDto);
        CommentResponseDto dto = res.getBody();

            CommentUpdateDto commentUpdateDto = new CommentUpdateDto("Titulo Atualizado", "Boa tarde pessoal");
            ResponseEntity<CommentResponseDto> response = commentConsumerFeing.update(dto.getId() , commentUpdateDto);

            Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));

            commentConsumerFeing.delete(dto.getId());
    }




    @Test
    public void updateComment_WithInvalidId_ThrowEntityNotFoundException(){

        CommentUpdateDto commentUpdateDto = new CommentUpdateDto("Titulo Atualizado", "Boa tarde pessoal");
        Assertions.assertThrows(EntityNotFoundException.class, () -> { commentConsumerFeing.update("TesteId01", commentUpdateDto);});

    }


    @Test
    public void CommentsUpdate_WithInvalidData_ExpectStatusBadRequestException(){
        CommentUpdateDto commentUpdateDto1 = new CommentUpdateDto("Ti", "Boa tarde pessoal");
        CommentUpdateDto commentUpdateDto2 = new CommentUpdateDto("Titulo", "Bo");
        CommentUpdateDto commentUpdateDto3 = new CommentUpdateDto(null, "Boa tarde");

        Assertions.assertThrows(BadRequestException.class, () -> { commentConsumerFeing.update("28", commentUpdateDto1);});
        Assertions.assertThrows(BadRequestException.class, () -> { commentConsumerFeing.update("28", commentUpdateDto2);});
        Assertions.assertThrows(BadRequestException.class, () -> { commentConsumerFeing.update("28", commentUpdateDto3);});
    }


    @Test
    public void list_ExpectStatus200WithListOfComments(){
        ResponseEntity<List<CommentResponseDto>> res = commentConsumerFeing.getAllComments();

        Assertions.assertEquals(res.getStatusCode(), HttpStatusCode.valueOf(200));

        Assertions.assertNotNull(res.getBody());


    }



    @Test
    public void getCommentById_WithValidId_ExpectStatus200(){
        CommentCreateDto commentCreateDto = new CommentCreateDto("test@gmail.com", "nicename", "paster", "28");
        ResponseEntity<CommentResponseDto> res = commentConsumerFeing.save(commentCreateDto);

        ResponseEntity<CommentResponseDto> read = commentConsumerFeing.getCommentById(res.getBody().getId());

        Assertions.assertNotNull(read.getBody());
        Assertions.assertEquals(read.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals(read.getBody().getName(),commentCreateDto.getName());
        Assertions.assertEquals(read.getBody().getBody(), commentCreateDto.getBody());

        commentConsumerFeing.delete(res.getBody().getId());

    }

    @Test
    public void getCommentByPostId_WithValidId_ExpectStatus200(){
        PostCreateDto postCreated = new PostCreateDto(23, "Titulo", "Bom dia pessoal");
        ResponseEntity<PostResponseDto> postRes = postConsumerFeign.save
                (postCreated);
        PostResponseDto dto =postRes.getBody();

        CommentCreateDto commentCreateDto = new CommentCreateDto("test@gmail.com", "nicename", "paster", dto.getId());
        ResponseEntity<CommentResponseDto> res = commentConsumerFeing.save(commentCreateDto);

        ResponseEntity<List<CommentResponseDto>> read = commentConsumerFeing.getCommentByPostId(dto.getId());

        Assertions.assertNotNull(read.getBody());
        Assertions.assertEquals(read.getStatusCode(), HttpStatusCode.valueOf(200));


        commentConsumerFeing.delete(res.getBody().getId());

    }

    @Test
    public void getCommentByPostId_WithInvalidId_ExpectStatus404(){

        Assertions.assertThrows(EntityNotFoundException.class, () -> { commentConsumerFeing.getCommentByPostId("EsseEumIdDeTesteInexistente");});

    }


    @Test
    public void getCommentById_WithInvalidId_ExpectStatus200(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> { commentConsumerFeing.getCommentById("TesteId01");});

    }

    @Test
    public void DeleteCommentById_WithValidId_ExpectStatus204(){
        CommentCreateDto commentCreateDto = new CommentCreateDto("test@gmail.com", "nicename", "paster", "28");
        ResponseEntity<CommentResponseDto> res = commentConsumerFeing.save(commentCreateDto);

        ResponseEntity read = commentConsumerFeing.delete(res.getBody().getId());

        Assertions.assertEquals(read.getStatusCode(), HttpStatusCode.valueOf(204));


    }

    @Test
    public void DeleteCommentById_WithNonExistentId_ThrowEntityNotFoundException(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> { commentConsumerFeing.delete("TesteId01");});
    }



    @Test
    public void getCommentByEmail_WithValidEmail_ExpectStatus200() {

        CommentCreateDto commentCreateDto = new CommentCreateDto("tank@gmail.com", "nicename", "paster", "28");
        ResponseEntity<CommentResponseDto> saveResponse = commentConsumerFeing.save(commentCreateDto);

        ResponseEntity<List<CommentResponseDto>> response = commentConsumerFeing.getByEmail(commentCreateDto.getEmail());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertFalse(response.getBody().isEmpty(), "A lista de comentários está vazia.");

        CommentResponseDto firstComment = response.getBody().get(0);

        ResponseEntity<CommentResponseDto> readResponse = commentConsumerFeing.getCommentById(firstComment.getId());
        Assertions.assertNotNull(readResponse.getBody());
        Assertions.assertEquals(readResponse.getBody().getName(), commentCreateDto.getName());
        Assertions.assertEquals(readResponse.getBody().getBody(), commentCreateDto.getBody());

        commentConsumerFeing.delete(firstComment.getId());
    }

    @Test
    public void getCommentByEmail_WithInvalidEmail_ExpectStatus200() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> { commentConsumerFeing.getByEmail(" ");});
    }


}

