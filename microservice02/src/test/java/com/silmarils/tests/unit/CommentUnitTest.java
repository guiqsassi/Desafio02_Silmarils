package com.silmarils.tests.unit;

import com.silmarils.microservice02.Microservice02Application;
import com.silmarils.microservice02.entities.Comment;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.exceptions.EntityNotFoundException;
import com.silmarils.microservice02.repository.CommentRepository;
import com.silmarils.microservice02.repository.PostRepository;
import com.silmarils.microservice02.services.CommentService;
import com.silmarils.microservice02.services.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = Microservice02Application.class)
public class CommentUnitTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void testCreatComment_WithValidData() {
        Post post = new Post("1", 2, "CommentUnitTesttestePost", "O corpo do post");
        Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", post);

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(commentRepository.save(comment1)).thenReturn(comment1);

        Comment comment = commentService.create(comment1);

        Assertions.assertNotNull(comment);
        Assertions.assertEquals(comment1.getId(), comment.getId());
        Assertions.assertEquals(comment1.getName(), comment.getName());
        Assertions.assertEquals(comment1.getBody(), comment.getBody());
        Assertions.assertEquals(comment1.getPostId().getId(), comment.getPostId().getId());
    }

    @Test
    public void testCreatComment_NonExistentPost() {
        Post post = new Post();
        post.setId("1");
        Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", post);

        when(postRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            commentService.create(comment1);
        });
    }

   @Test
    public void getComment_WithExinstentId() {
       Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", null);

       when(commentRepository.findById(any())).thenReturn(Optional.of(comment1));

       Comment commentFinded = commentService.findById(comment1.getId());

       Assertions.assertNotNull(commentFinded);
       Assertions.assertEquals(commentFinded.getId(), comment1.getId());
       Assertions.assertEquals(commentFinded.getName(), comment1.getName());
       Assertions.assertEquals(commentFinded.getBody(), comment1.getBody());

   }

    @Test
    public void getComment_WithNonExinstentId() {
        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            commentService.findById("1");
        });
    }

    @Test
    public void testDeleteComment_WithExistentId(){
        Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", null);

        when(commentRepository.existsById(comment1.getId())).thenReturn(true);

        assertThatCode(() -> commentService.delete(comment1.getId()))
                .doesNotThrowAnyException();

        verify(commentRepository).deleteById(comment1.getId());
    }

    @Test
    public void testDeleteComment_WithNonExistentId(){

        when(commentRepository.existsById(any())).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            commentService.delete("1");
        });

    }

    @Test
    public void testGetAllComments_WithValidPostId(){
        Post post = new Post();
        post.setId("1");
        Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", post);
        Comment comment2 = new Comment("2", "mary@gmail.com", "nome2", "corpo do comentario2", post);
        Comment comment3 = new Comment("3", "claudio@gmail.com", "nome3", "corpo do comentario3", post);
        when(commentRepository.findByPostId(any())).thenReturn(List.of(comment3, comment2, comment1));

        List<Comment> commentList = commentService.findByPost(post.getId());

        Assertions.assertNotNull(commentList);
        Assertions.assertEquals(3, commentList.size());

    }

    @Test
    public void testGetAllComments_WithInvalidPostId(){

        when(postRepository.existsById(any())).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            commentService.findByPost("1");
        });

    }

    @Test
    public void testGetAllComments(){

        Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", null);
        Comment comment2 = new Comment("2", "mary@gmail.com", "nome2", "corpo do comentario2", null);
        Comment comment3 = new Comment("3", "claudio@gmail.com", "nome3", "corpo do comentario3", null);
        when(commentRepository.findAll()).thenReturn(List.of(comment3, comment2, comment1));

        List<Comment> commentList = commentService.findAll();

        Assertions.assertNotNull(commentList);
        Assertions.assertEquals(3, commentList.size());

    }


    @Test
    public void testCommentUpdate_withValidData(){
        Comment commentBefore = new Comment("1", "mary@gmail.com", "nome Antes de atualizar", "corpo do comentario", null);

        Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", null);

        when(commentRepository.save(comment1)).thenReturn(comment1);
        when(commentRepository.findById(comment1.getId())).thenReturn(Optional.of(commentBefore));


        Comment comment = commentService.update( "1",comment1);

        Assertions.assertNotNull(comment);
        Assertions.assertEquals(comment1.getId(), comment.getId());
        Assertions.assertEquals(comment1.getName(), comment.getName());
        Assertions.assertEquals(comment1.getBody(), comment.getBody());

    }

    @Test
    public void testCommentUpdate_withNonExistentId(){

        when(commentRepository.findById("1")).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            commentService.update("1", null);
        });

    }


    @Test
    public void testGetCommentByEmail(){

        Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", null);
        Comment comment2 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", null);
        when(commentRepository.findAllByEmail("mary@gmail.com")).thenReturn(List.of(comment1, comment2));

        List<Comment> commentList = commentService.findByEmail("mary@gmail.com");

        Assertions.assertNotNull(commentList);
        Assertions.assertEquals(2, commentList.size());

    }



    @Test
    public void testGetCommentByIncorrectEmail(){

        Comment comment1 = new Comment("1", "mary@gmail.com", "nome1", "corpo do comentario", null);
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            commentService.findByEmail("mary@gmail.com");
        });

    }
}
