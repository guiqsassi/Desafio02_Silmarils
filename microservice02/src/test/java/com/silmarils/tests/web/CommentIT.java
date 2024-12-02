package com.silmarils.tests.web;

import com.silmarils.microservice02.Microservice02Application;
import com.silmarils.microservice02.entities.Comment;
import com.silmarils.microservice02.repository.CommentRepository;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.repository.PostRepository;
import com.silmarils.microservice02.web.controllers.exception.ErrorMessage;
import com.silmarils.microservice02.web.dto.CommentCreateDto;
import com.silmarils.microservice02.web.dto.CommentResponseDto;
import com.silmarils.microservice02.web.dto.CommentUpdateDto;
import com.silmarils.tests.config.EmbeddedMongoConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.List;



@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Microservice02Application.class)
public class CommentIT {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    public void setup(){
        postRepository.deleteAll();
        commentRepository.deleteAll();

        Post post = new Post("1", 2, "testePost", "O corpo do post");
        Post post2 = new Post("2", 2, "Post de teste 2", "O corpo do post de teste 2");
        Post post3 = new Post("3", 2, "Post de teste 3", "O corpo do post de teste 3");

        Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", post);
        Comment comment2 = new Comment("2", "mary@gmail.com", "nome2", "corpo do comentario2", post);
        Comment comment3 = new Comment("3", "claudio@gmail.com", "nome3", "corpo do comentario3", post2);
        Comment comment4 = new Comment("4", "claudio@gmail.com", "nome4", "corpo do comentario4", post2);
        Comment comment5 = new Comment("5", "douglas@gmail.com", "nome5", "corpo do comentario5", post3);
        Comment comment6 = new Comment("6", "douglas@gmail.com", "nome6", "corpo do comentario6", post3);
        post.getComments().add(comment1);
        post.getComments().add(comment2);
        post2.getComments().add(comment3);
        post2.getComments().add(comment4);
        post3.getComments().add(comment5);
        post3.getComments().add(comment6);

        commentRepository.saveAll(List.of(comment1, comment2, comment3, comment4, comment5, comment6));
        postRepository.saveAll(List.of(post, post2, post3));
    }

    @Test
    public void commentCreate_WithValidInputData_ShouldReturnCommentWithStatus201(){

        CommentCreateDto dto = new CommentCreateDto("teste@gmail.com", "nome", "corpo do texto", "1");

        CommentResponseDto res = webClient
                .post()
                .uri("/api/comments")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CommentResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res.getId()).isNotNull();
        Assertions.assertThat(res.getBody()).isEqualTo(dto.getBody());
        Assertions.assertThat(res.getEmail()).isEqualTo(dto.getEmail());
        Assertions.assertThat(res.getName()).isEqualTo(dto.getName());

        Post post = postRepository.findById("1").orElse(null);
        Assertions.assertThat(post.getComments().isEmpty()).isFalse();

    }

    @Test
    public void commentCreate_WithInvalidInputData_ShouldReturnErrorMessageWithStatus400(){

        CommentCreateDto dto = new CommentCreateDto("teste", "nome", "corpo do texto", "1");

        ErrorMessage res = webClient
                .post()
                .uri("/api/comments")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res.getStatus()).isEqualTo(400);

    }

    @Test
    public void commentCreate_WithNonExistentPostId_ShouldReturnErrorMessageWithStatus400(){

        CommentCreateDto dto = new CommentCreateDto("teste@gmail.com", "nome", "corpo do texto", "100-");

        ErrorMessage res = webClient
                .post()
                .uri("/api/comments")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res.getStatus()).isEqualTo(404);

    }


    @Test
    public void commentGetById_WithExistentId_ShouldReturnCommentWithStatus200(){

        CommentResponseDto res = webClient
                .get()
                .uri("/api/comments/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentResponseDto.class)
                .returnResult().getResponseBody();


        Assertions.assertThat(res.getId()).isNotNull();
        Assertions.assertThat(res.getBody()).isEqualTo("corpo do comentario");
    }

    @Test
    public void commentGetById_WithNonExistentId_ShouldReturnErrorMessageWithStatus404(){

        ErrorMessage res = webClient
                .get()
                .uri("/api/comments/100")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res.getStatus()).isEqualTo(404);
    }

    @Test
    public void getAll_ShouldReturnListOfCommentsWithStatus200(){

        List<CommentResponseDto> res = webClient
                .get()
                .uri("/api/comments")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
    }

    @Test
    public void getAllByPostId_WithExistentPostId_ShouldReturnListOfCommentsWithStatus200(){

        List<CommentResponseDto> res = webClient
                .get()
                .uri("/api/comments/post/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res).isNotEmpty();
        Assertions.assertThat(res).hasSize(2);
    }

    @Test
    public void getAllByPostId_WithNonExistentPostId_ShouldReturnListOfCommentsWithStatus200(){

        ErrorMessage res = webClient
                .get()
                .uri("/api/comments/post/1000")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.getStatus()).isEqualTo(404);
    }




    @Test
    public void commentDeleteById_WithExistentId_ShouldReturnStatus204() {

        webClient
                .delete()
                .uri("/api/comments/1")
                .exchange()
                .expectStatus().isNoContent();

        Assertions.assertThat(commentRepository.existsById("1")).isFalse();
    }

    @Test
    public void commentDeleteById_WithNonExistentId_ShouldReturnStatus404(){

        ErrorMessage res = webClient
                .delete()
                .uri("/api/comments/100")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(res.getStatus()).isEqualTo(404);

    }


    @Test
    public void updateComment_WithExistentId_ShouldReturnCommentStatus200(){
        CommentUpdateDto dto = new CommentUpdateDto("Teste 01", "Corpo de teste");

        CommentResponseDto res = webClient
                .put()
                .uri("/api/comments/1")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentResponseDto.class).returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.getId()).isNotNull();
        Assertions.assertThat(res.getBody()).isEqualTo(dto.getBody());
        Assertions.assertThat(res.getName()).isEqualTo(dto.getName());

    }

    @Test
    public void updateComment_WithNonExistentId_ShouldReturnCommentStatus404(){
        CommentUpdateDto dto = new CommentUpdateDto("Teste 01", "Corpo de teste");

        ErrorMessage res = webClient
                .put()
                .uri("/api/comments/100")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(res.getStatus()).isEqualTo(404);

    }

    @Test
    public void updateComment_WithInvalidData_ShouldReturnCommentStatus400(){
        CommentUpdateDto dto = new CommentUpdateDto("Tes", "Corpo de teste");

        ErrorMessage res = webClient
                .put()
                .uri("/api/comments/100")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(res.getStatus()).isEqualTo(400);

    }


    @Test
    public void FindComment_WithExistentEmail_ShouldReturnCommentStatus200(){

        List<CommentResponseDto> res = webClient
                .put()
                .uri("/api/comments/email/Brennon@carmela.tv")
                .exchange()
                .expectBodyList(CommentResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();

    }

    @Test
    public void FindComment_WithNonExistentEmail_ShouldReturnCommentStatus404(){
        ErrorMessage res = webClient
                .put()
                .uri("/api/comments/email/marc@gmail.")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(res.getStatus()).isEqualTo(400);


    }



}

