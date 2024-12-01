package com.silmarils.microservice02.services;

import com.silmarils.microservice02.entities.Comment;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.feignClients.CommentConsumerFeign;
import com.silmarils.microservice02.feignClients.PostConsumerFeign;
import com.silmarils.microservice02.repository.CommentRepository;
import com.silmarils.microservice02.repository.PostRepository;
import com.silmarils.microservice02.web.dto.CommentFeignDto;
import com.silmarils.microservice02.web.dto.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostSyncService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostConsumerFeign postConsumerFeign;

    @Autowired
    private CommentConsumerFeign commentConsumerFeign;

    public List<Post> syncData() {
        List<Post> posts = postConsumerFeign.list().getBody();
        List<CommentFeignDto> commentsDto = commentConsumerFeign.getComments().getBody();
        List<Comment> comments = CommentMapper.toListComment(commentsDto);


        comments.forEach(comment -> {
           posts.forEach(post -> {
               if(comment.getPostId().getId().equals(post.getId())) {
                   post.getComments().add(comment);
                   comment.setPostId(post);
               }
           });
        });


        if(posts.isEmpty()) {
            throw new RuntimeException("There are no posts");
        }

        commentRepository.saveAll(comments);
        return postRepository.saveAll(posts);
    }
}
