package com.silmarils.tests.unit;

import com.silmarils.microservice02.Microservice02Application;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.exceptions.EntityNotFoundException;
import com.silmarils.microservice02.repository.PostRepository;
import com.silmarils.microservice02.services.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = Microservice02Application.class)
public class PostUnitTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    public void postFindById_WithCorrectId(){
        Post post = new Post("2", 4, "test do titulo do post", "test do corpo do post");
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Post postFind = postService.findById(post.getId());

        Assertions.assertNotNull(postFind);
        assertEquals(post.getId(), postFind.getId());
        assertEquals(post.getTitle(), postFind.getTitle());
        assertEquals(post.getBody(), postFind.getBody());


    }
    @Test
    public void postFindById_WithIncorrectId(){
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            postService.findById("3");
        });
    }
    @Test
    public void getAllPosts_correct(){
        Post post1 = new Post("1", 3, "test do titulo do post2024", "test do corpo do post");
        Post post2 = new Post("2", 4, "test do titulo do post", "test do corpo do post");
        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));

        List<Post> posts = postService.findAll();
    }



}
