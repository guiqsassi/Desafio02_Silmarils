package com.silmarils.microservice02.repository;

import com.silmarils.microservice02.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByEmail(String email);
}
