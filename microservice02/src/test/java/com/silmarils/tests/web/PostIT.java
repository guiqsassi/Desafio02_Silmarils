package com.silmarils.tests.web;

import com.silmarils.microservice02.Microservice02Application;
import com.silmarils.microservice02.dto.PostResponseDto;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.repository.PostRepository;
import com.silmarils.microservice02.web.controllers.exception.ErrorMessage;
import com.silmarils.tests.config.EmbeddedMongoConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;
import static org.mockito.Mockito.when;


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

        webClient.get()
                .uri("/api/posts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostResponseDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    public void testGetPost_WithNonExistentId_ShouldReturnStatus404()  throws Exception {

        webClient.get()
                .uri("/api/posts/6")
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(404))
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }

    @Test
    public void testgetall_withid(){
        webClient.get()
                .uri("/api/posts")
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(200))
                .expectBodyList(PostResponseDto.class)
                .returnResult().getResponseBody();

        List<Post> posts = postRepository.findAll();}


}

