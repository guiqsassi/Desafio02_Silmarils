package com.silmarils.microservice02.services;

import com.silmarils.microservice02.entities.Comment;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.exceptions.EntityNotFoundException;
import com.silmarils.microservice02.repository.CommentRepository;
import com.silmarils.microservice02.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment create(Comment comment) {
        Post post = postRepository.findById(comment.getPostId().getId())
                .orElseThrow(()-> new EntityNotFoundException("Post not found with id: " + comment.getPostId().getId()));
        comment.setPostId(post);
        Comment commentCreated =commentRepository.save(comment);
        post.getComments().add(commentCreated);
        postRepository.save(post);

        return commentCreated;

    }

    public List<Comment> findAll() {

        return commentRepository.findAll();
    }

    public Comment findById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("comment with id: " + id + " not found"));
    }

    public Comment update(String id, Comment comment) {
        Comment commentToUpdate = findById(id);
        commentToUpdate.setBody(comment.getBody());
        commentToUpdate.setName(comment.getName());

        return commentRepository.save(commentToUpdate);
    }

    public void delete(String id) {
        if(!commentRepository.existsById(id)){
            throw new RuntimeException("comment with id: " + id + " not found");
        }
        commentRepository.deleteById(id);
    }


}
