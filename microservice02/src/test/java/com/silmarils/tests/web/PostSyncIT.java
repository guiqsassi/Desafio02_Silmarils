package com.silmarils.tests.web;

import com.silmarils.microservice02.Microservice02Application;
import com.silmarils.microservice02.dto.PostResponseDto;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.repository.PostRepository;
import com.silmarils.tests.config.EmbeddedMongoConfig;
import io.swagger.v3.oas.models.links.Link;
import org.junit.jupiter.api.Assertions;
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
public class PostSyncIT {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void syncData_ReturnListOfPostsStatus200(){

        // Testa com a conexão com JsonPlaceHolder
        var list= webClient.post().uri("/api/v1/sync-data")
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(PostResponseDto.class)
                .hasSize(100)
                .returnResult().getResponseBody();

        Assertions.assertEquals(list.size(), 100);

        //Testa para ver se os posts foram salvos
        List<Post> savedData= postRepository.findAll();

        Assertions.assertEquals(savedData.size(), 100);
    }



}
