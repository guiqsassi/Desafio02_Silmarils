package com.silmarils.microservice02;
import com.silmarils.microservice02.entities.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Microservice02Application.class)
public class PostIT {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MockMvc client;

    @BeforeEach
    void setUp() throws Exception {
        mongoTemplate.dropCollection("tb_post");
        mongoTemplate.insert(new Post("1", 2, "titulo do teste", "Esse texto é apenas um teste"));
    }

    @Test
    public void testGetPost_WithExistingId_ShouldReturnPostWithStatus200() throws Exception {

        client.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(2));
    }

    @Test
    public void testGetPost_WithNonExistentId_ShouldReturnStatus404() throws Exception {

        client.perform(get("/api/v1/posts/999"))
                .andExpect(status().isNotFound());
    }


}
