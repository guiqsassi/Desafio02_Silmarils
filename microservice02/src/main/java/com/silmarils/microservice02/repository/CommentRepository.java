package com.silmarils.microservice02.repository;

import com.silmarils.microservice02.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query("{'postId.id': ?0}")
    List<Comment> findByPostId(String postId);


    List<Comment> findAllByEmail(String email);

    void deleteAllByPostId_Id(String postId);

}
