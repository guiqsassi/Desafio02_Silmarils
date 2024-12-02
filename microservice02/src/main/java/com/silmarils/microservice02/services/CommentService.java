package com.silmarils.microservice02.services;

import com.silmarils.microservice02.entities.Comment;
import com.silmarils.microservice02.entities.Post;
import com.silmarils.microservice02.exceptions.EntityNotFoundException;
import com.silmarils.microservice02.repository.CommentRepository;
import com.silmarils.microservice02.repository.PostRepository;

import com.silmarils.microservice02.web.controllers.exception.ErrorMessage;
import com.silmarils.microservice02.web.dto.CommentUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Tag(name = "post", description = "post of user")
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Operation(
            summary = "post creation" ,description = "creating a post for a user",responses = {

                    @ApiResponse(responseCode = "201",description = "meta for post creation",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "400",description = "error creating with repeated id",content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorMessage.class)))
    }

    )
    public Comment create(Comment comment) {
        Post post = postRepository.findById(comment.getPostId().getId())
                .orElseThrow(()-> new EntityNotFoundException("Post not found with id: " + comment.getPostId().getId()));
        comment.setPostId(post);
        Comment commentCreated =commentRepository.save(comment);
        post.getComments().add(commentCreated);
        postRepository.save(post);

        return commentCreated;

    }

    @Operation(
            summary = "method to find everyone" ,description = "this goal seeks everyone",responses = {

            @ApiResponse(responseCode = "200",description = "this goal seeks everyone",content = @Content(mediaType = "application/json",schema = @Schema(implementation = CommentRepository.class))),

    }

    )
    public List<Comment> findAll() {

        return commentRepository.findAll();
    }


    @Operation(
            summary = "This method searches for  IDs" ,description = "success in searching by id",responses = {

            @ApiResponse(responseCode = "201",description = "method created by success",content = @Content(mediaType = "application/json",schema = @Schema(implementation = CommentRepository.class))),
            @ApiResponse(responseCode = "404",description = "error in searching for invalid id",content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorMessage.class)))
    }

    )
    public Comment findById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("comment with id: " + id + " not found"));
    }



    @Operation(
            summary = "metadata that updates the content" ,description = "method that updates content based on user id",responses = {

            @ApiResponse(responseCode = "201",description = "method updated successfully",content = @Content(mediaType = "application/json",schema = @Schema(implementation = CommentUpdateDto.class))),
            @ApiResponse(responseCode = "404",description = "metado gave an error because it did not recognize the id",content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "400",description = "method gave an error because the updated content was the same as what existed",content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorMessage.class)))
    }

    )
    public Comment update(String id, Comment comment) {
        Comment commentToUpdate = findById(id);
        commentToUpdate.setBody(comment.getBody());
        commentToUpdate.setName(comment.getName());

        return commentRepository.save(commentToUpdate);
    }


    @Operation(
            summary = "method of deleting the comment from the id" ,description = "metado deletes the post content by searching for existing id",responses = {

            @ApiResponse(responseCode = "204",description = "metado was successful in deleting the post",content = @Content(mediaType = "application/json",schema = @Schema(implementation = CommentUpdateDto.class))),
            @ApiResponse(responseCode = "404",description = "method was unsuccessful in returning because the id was invalid",content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorMessage.class)))}


    )

    public void delete(String id) {
        Comment commentToDelete = findById(id);

        Post post = postRepository.findById(commentToDelete.getPostId().getId()).orElseThrow(() ->
                new EntityNotFoundException("Post with id: " + id + " not found"));

        post.getComments().remove(commentToDelete);
        postRepository.save(post);
        commentRepository.deleteById(id);
    }


}
