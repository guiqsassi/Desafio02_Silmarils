package com.silmarils.microservice01.web.controllers;

import com.silmarils.microservice01.dtos.CommentCreateDto;
import com.silmarils.microservice01.dtos.CommentResponseDto;
import com.silmarils.microservice01.dtos.CommentUpdateDto;
import com.silmarils.microservice01.feignClients.CommentConsumerFeing;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@Tag(name = "comments", description = "comments from post users")
public class CommentController {

    @Autowired
    public CommentConsumerFeing commentConsumerFeing;

    @Operation(
            summary = "create a new comment", description = "resources to create a new comment",
            responses = {
                    @ApiResponse(responseCode = "201", description = "resource created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    )
                    ,@ApiResponse( responseCode = "409", description = "error creating code", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)))
            }
    )

    @PostMapping
    public ResponseEntity<CommentResponseDto> create (@RequestBody CommentCreateDto commentCreateDto) {
        return commentConsumerFeing.save(commentCreateDto);
    }

    @Operation(
           summary = "update comment content" , description = "resource to update a comment", responses = {
                  @ApiResponse (responseCode = "200", description = "comment updated successfully", content = @Content(mediaType = "application/json", schema =  @Schema (implementation = CommentUpdateDto.class))),
            @ApiResponse (responseCode = "409", description = "id not registered error", content = @Content(mediaType = "application/json", schema =  @Schema (implementation = Exception.class))),
            @ApiResponse (responseCode = "400", description = "error in web request", content = @Content(mediaType = "application/json", schema =  @Schema (implementation = Exception.class)))
    }

    )
    @PutMapping("{id}")
    public ResponseEntity<CommentResponseDto> update (@PathVariable("id") String id, @RequestBody CommentUpdateDto commentUpdateDto) {
        return commentConsumerFeing.update(id, commentUpdateDto);
    }

    @Operation(
            summary = "method to return all comments", description = "method to return all comments made", responses = {
            @ApiResponse(responseCode = "200", description = "success in returning the list", content = @Content (mediaType = "application/json", schema = @Schema (implementation = CommentCreateDto.class)))
            }
    )

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {return commentConsumerFeing.getAllComments();}

    @Operation(
            summary = "method that returns the comment by id", description = "method used to return specific comments by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success returning comments by id", content = @Content (mediaType = "application?json", schema = @Schema(implementation = CommentResponseDto.class)))
                    ,@ApiResponse(responseCode = "404", description = "invalid id", content = @Content(mediaType ="application/json", schema = @Schema(implementation = Exception.class)))
            }
    )

    @GetMapping("{id}")
    public  ResponseEntity<CommentResponseDto> getCommentById(@PathVariable String id) { return commentConsumerFeing.getCommentById(id);}

    @Operation(
            summary = "deleted method", description = "method that deletes content by id", responses = {
                    @ApiResponse(responseCode = "204", description = "delete done successfully", content = @Content(mediaType = "application/json" )),
                @ApiResponse(responseCode = "404", description = "error when deleting due to not finding the id", content = @Content (mediaType = "application/json", schema = @Schema(implementation = Exception.class)))
            }
    )

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) { return commentConsumerFeing.delete(id);}
}