package com.silmarils.microservice02.services;

import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.exceptions.EntityNotFoundException;
import com.silmarils.microservice02.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.silmarils.microservice02.repository.PostRepository;
import java.util.List;




@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Post create(Post post) {
        try {
            return postRepository.save(post);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Post findById(String id)
    {

        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        return post;
    }

    public void delete(String id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Post with id %s not found, not possible to delete", id)));

        commentRepository.deleteAllByPostId_Id(id);
        postRepository.deleteById(id);
    }

    public Post update(Post postNewData, String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Post with id %s not found", id)));

        if(!postNewData.getBody().equals(post.getBody())){
            post.setBody(postNewData.getBody());
        }
        if(!postNewData.getTitle().equals(post.getTitle())){
            post.setTitle(postNewData.getTitle());
        }

        return postRepository.save(post);
        }

    public List<Post> findAll(){
        return postRepository.findAll();
    }



}
