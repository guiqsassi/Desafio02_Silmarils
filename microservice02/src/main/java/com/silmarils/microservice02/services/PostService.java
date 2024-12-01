package com.silmarils.microservice02.services;

import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import com.silmarils.microservice02.repository.PostRepository;

import java.util.List;
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

        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        return post;
    }

    public void delete(String id) {
        if(id == null || !postRepository.existsById(id)){
            throw new EntityNotFoundException(String.format("Post with id %s not found, not possible to delete", id));
        }

        postRepository.deleteById(id);
    }

    public Post update(Post postNewData, String id) {
        Post post = this.findById(id);

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
