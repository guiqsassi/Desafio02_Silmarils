package com.silmarils.microservice02.feignClients;

import com.silmarils.microservice02.web.dto.CommentFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "CommentConsumer",  url = "https://jsonplaceholder.typicode.com/")
public interface CommentConsumerFeign {
    @GetMapping("/comments")
    ResponseEntity<List<CommentFeignDto>> getComments();
}
