package com.silmarils.microservice01.web.controllers;

import com.silmarils.microservice01.entities.Post;
import com.silmarils.microservice01.feignClients.PostConsumerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostConsumerFeign postConsumerFeign;

    @GetMapping("")
    public ResponseEntity<List<Post>> list(){
        return postConsumerFeign.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable String id){
        return postConsumerFeign.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        return postConsumerFeign.delete(id);
    }
    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post){
        return postConsumerFeign.save(post);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable String id, @RequestBody Post post){
        return postConsumerFeign.update(id, post);
    }


}
