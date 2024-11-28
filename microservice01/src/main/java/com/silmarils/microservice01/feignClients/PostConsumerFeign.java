package com.silmarils.microservice01.feignClients;

import com.silmarils.microservice01.entities.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "post-consumer", url = "http://localhost:8081/api/posts")
public interface PostConsumerFeign {
    @GetMapping("")
    public ResponseEntity<List<Post>> list();

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable("id") String id);

    @PostMapping("")
    public ResponseEntity<Post> save(@RequestBody Post post);

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable("id") String id, @RequestBody Post post);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id);
}
