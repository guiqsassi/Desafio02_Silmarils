package com.silmarils.microservice02.feignClients;

import com.silmarils.microservice02.entities.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "post-consumer", url = "https://jsonplaceholder.typicode.com/")
public interface PostConsumerFeign {
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> list();
}
