package com.silmarils.microservice02.services;

import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import com.silmarils.microservice02.repository.PostRepository;

import java.util.Optional;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post create(Post post) {
        try {
            return postRepository.save(post);
        }catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Post findById(String id)
    {
        if(id == null || !postRepository.existsById(id)){
            throw new EntityNotFoundException(String.format("Post with id %s not found", id));
        }
        Post post = postRepository.findById(id).get();

        return post;
    }

    public void delete(String id) {
        if(id == null || !postRepository.existsById(id)){
            throw new EntityNotFoundException(String.format("Post with id %s not found, not possible to delete", id));
        }

        postRepository.deleteById(id);
    }
        public Post update(Post pst) {
        Post post = postRepository.findById(pst.getId()).get();
        updatepost(post,pst);
        return postRepository.save(post);

        }

    private void updatepost(Post post, Post pst) {
        post.setTitle(pst.getTitle());
        post.setBody(pst.getBody());
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }





}
