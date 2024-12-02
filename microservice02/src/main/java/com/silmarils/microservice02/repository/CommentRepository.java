package com.silmarils.microservice02.repository;

import com.silmarils.microservice02.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query("{'postId.id': ?0}")
    List<Comment> findByPostId(String postId);

    void deleteAllByPostId_Id(String postId);
}
