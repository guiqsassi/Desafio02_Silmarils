package com.silmarils.microservice02.services;

import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.feignClients.PostConsumerFeign;
import com.silmarils.microservice02.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostSyncService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostConsumerFeign postConsumerFeign;


    public List<Post> syncData() {
        List<Post> posts = postConsumerFeign.list().getBody();
        if(posts.isEmpty()) {
            throw new RuntimeException("There are no posts");
        }

        return postRepository.saveAll(posts);
    }
}
