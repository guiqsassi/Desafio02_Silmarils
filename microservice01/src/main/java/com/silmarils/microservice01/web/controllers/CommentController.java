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
@Tag(name ="comentarios" , description = "comentarios do usuarios do post")
public class CommentController {

    @Autowired
    public CommentConsumerFeing commentConsumerFeing;


    @Operation(
            summary = "criar um novo comentario" , description = "recursos para criar um novo comentario",
            responses = {
                    @ApiResponse(responseCode = "201" , description = "recurso criado com sucesso",content = @Content(mediaType = "aplication/json" , schema = @Schema(implementation = CommentResponseDto.class))
                    )
                    ,@ApiResponse( responseCode = "409 " , description = "erro na criação do codigo",content = @Content(mediaType = "aplication/json" ,schema =@Schema(implementation = Exception.class)))
            }

    )



    @PostMapping
    public ResponseEntity<CommentResponseDto> create (@RequestBody CommentCreateDto commentCreateDto) {
        return commentConsumerFeing.save(commentCreateDto);
    }


    @Operation(
           summary = "update do conteudo do comentario " , description = "recurso para atualizar um cmentario ",responses = {
                  @ApiResponse (responseCode = "200" , description = "cometario atulizado com exito" ,content = @Content(mediaType = "aplication/json" ,schema =  @Schema (implementation = CommentUpdateDto.class))),
            @ApiResponse (responseCode = "409" , description = "erro de id não cadastrado" ,content = @Content(mediaType = "aplication/json" ,schema =  @Schema (implementation = Exception.class))),
            @ApiResponse (responseCode = "400" , description = "erro na requisão web" ,content = @Content(mediaType = "aplication/json" ,schema =  @Schema (implementation = Exception.class)))
    }

    )
    @PutMapping("{id}")
    public ResponseEntity<CommentResponseDto> update (@PathVariable("id") String id, @RequestBody CommentUpdateDto commentUpdateDto) {
        return commentConsumerFeing.update(id, commentUpdateDto);
    }



    @Operation(
            summary = "metado para retorna todos comentarios " , description = "esse metado retorna todos comentarios feitos" ,responses = {
            @ApiResponse(responseCode = "200" , description = "sucesso ao retorna a lista " , content = @Content (mediaType = "aplication/json",schema = @Schema (implementation = CommentCreateDto.class)))

            }

    )
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {return commentConsumerFeing.getAllComments();}

    @Operation(
            summary = "metado que retona o comentario por id " ,description = "metado utilizado para retonar cometarios especificos por id",
            responses = {

                    @ApiResponse(responseCode = "200" ,description = "sucesso ao retorna cometarios por id" ,content = @Content (mediaType = "aplication?json" , schema = @Schema(implementation = CommentResponseDto.class)))
                    ,@ApiResponse(responseCode = "404" ,description = "id invalido ",content = @Content(mediaType ="aplication/json" ,schema = @Schema(implementation = Exception.class)))
            }
    )

    @GetMapping("{id}")
    public  ResponseEntity<CommentResponseDto> getCommentById(@PathVariable String id) { return commentConsumerFeing.getCommentById(id);}


    @Operation(
            summary = "metado delet" ,description = "metado que deleta o conteudo por id ",responses = {
                    @ApiResponse(responseCode = "204" ,description = "delete feito com sucesso " ,content = @Content(mediaType = "aplication/json" )),
                @ApiResponse(responseCode = "404" , description = "delete deu erro por não encontra o id" ,content = @Content (mediaType = "aplication/json" ,schema =@Schema(implementation = Exception.class)))



            }


    )
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) { return commentConsumerFeing.delete(id);}


}
