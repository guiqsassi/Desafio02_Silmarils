package com.silmarils.tests.web;

import com.silmarils.microservice02.Microservice02Application;
import com.silmarils.microservice02.web.dto.PostCreateDto;
import com.silmarils.microservice02.web.dto.PostResponseDto;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.repository.PostRepository;
import com.silmarils.microservice02.web.controllers.exception.ErrorMessage;
import com.silmarils.microservice02.web.dto.PostUpdateDto;
import com.silmarils.tests.config.EmbeddedMongoConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.List;



@Import(EmbeddedMongoConfig.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Microservice02Application.class)
public class PostIT {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    public void setup(){
        postRepository.deleteAll();Post post = new Post("1", 2, "testePost", "O corpo do post");
        Post post2 = new Post("2", 2, "Post de teste 2", "O corpo do post de teste 2");
        Post post3 = new Post("3", 2, "Post de teste 3", "O corpo do post de teste 3");

        postRepository.saveAll(List.of(post, post2, post3));
    }

    @Test
    public void testGetPost_WithExistingId_ShouldReturnPostWithStatus200(){

        PostResponseDto res = webClient.get()
                .uri("/api/posts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.getId()).isEqualTo("1");
        Assertions.assertThat(res.getUserId()).isEqualTo(2);
        Assertions.assertThat(res.getTitle()).isEqualTo("testePost");
    }

    @Test
    public void testGetPost_WithNonExistentId_ShouldReturnStatus404()  throws Exception {

        ErrorMessage res =webClient.get()
                .uri("/api/posts/6")
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(404))
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.getStatus()).isEqualTo(404);

    }

    @Test
    public void testDeletePost_WitCorrectId_ShouldReturnStatus204()  throws Exception {

        webClient.delete()
                .uri("/api/posts/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .returnResult().getResponseBody();

        Post post = postRepository.findById("1").orElse(null);

        Assertions.assertThat(post).isNull();

    }
  
@Test
    public void testDeletePost_WitIncorrectId_ShouldReturnStatus404()  throws Exception {

    var errorResponse = webClient.delete()
            .uri("/api/posts/6")
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
            .expectBody(ErrorMessage.class)
            .returnResult()
            .getResponseBody();


        org.assertj.core.api.Assertions.assertThat(errorResponse).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorResponse.getMessage()).isEqualTo("Post with id 6 not found, not possible to delete");
    }

    @Test
    public void testGetAll_ShouldReturnListOfPostsStatus200(){
        webClient.get()
                .uri("/api/posts")
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(200))
                .expectBodyList(PostResponseDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    public void testCreatePost_WithValidData_ShouldReturnPostResponseDtoWithStatus201() {
        PostCreateDto dto = new PostCreateDto(2, "Teste de criacao", "Corpo do post");

        PostResponseDto res = webClient.post()
                .uri("/api/posts")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(201))
                .expectBody(PostResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.getId()).isNotNull();
        Assertions.assertThat(res.getUserId()).isEqualTo(2);
        Assertions.assertThat(res.getTitle()).isEqualTo(dto.getTitle());
        Assertions.assertThat(res.getBody()).isEqualTo(dto.getBody());
    }

    @Test
    public void testCreatePost_WithInvalidData_ShouldReturnErrorMessageWithStatus400() {
        PostCreateDto dto = new PostCreateDto(2, "Tes", "Corpo do post");

        ErrorMessage res = webClient.post()
                .uri("/api/posts")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(400))
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.getStatus()).isEqualTo(400);
    }

    @Test
    public void testUpdatePost_WithValidData_ShoudReturnPostLocaleWithStatus200() {
        PostUpdateDto dto = new PostUpdateDto("Teste de atualização", "Corpo do post");

        String res = webClient.put()
                .uri("/api/posts/1")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(200))
                .expectBody(String.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Post post = postRepository.findById("1").orElse(null);
        Assertions.assertThat(post).isNotNull();
        Assertions.assertThat(post.getTitle()).isEqualTo(dto.getTitle());
        Assertions.assertThat(post.getBody()).isEqualTo(dto.getBody());


    }

    @Test
        public void testUpdatePost_WithInvalidId_ShoudReturnErrorMessageWithStatus404() {
            PostUpdateDto dto = new PostUpdateDto("Teste de atualização", "Corpo do post");

        ErrorMessage res = webClient.put()
                .uri("/api/posts/71")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(404))
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.getStatus()).isEqualTo(404);


    }

    @Test
    public void testUpdatePost_WithInvalidData_ShoudReturnErrorMessageWithStatus400() {
        PostUpdateDto dto = new PostUpdateDto("Tes", "Corpo do post");

        ErrorMessage res = webClient.put()
                .uri("/api/posts/1")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(400))
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.getStatus()).isEqualTo(400);

        dto = new PostUpdateDto("Teste", "Corp");

        res = webClient.put()
                .uri("/api/posts/1")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(400))
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.getStatus()).isEqualTo(400);

    }



}

