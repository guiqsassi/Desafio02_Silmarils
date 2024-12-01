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
    private PostService postService;

    public Comment create(Comment comment) {
        Post post = postService.findById(comment.getPostId().getId());
        comment.setPostId(post);
        return commentRepository.save(comment);
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
