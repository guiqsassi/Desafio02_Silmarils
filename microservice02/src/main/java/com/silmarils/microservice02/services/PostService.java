package com.silmarils.microservice02.services;

import com.silmarils.microservice02.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.silmarils.microservice02.repository.PostRepository;


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

    public void delete(long id) {
        postRepository.deleteById(id);
    }


}
