package com.silmarils.microservice02.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CommentResponseDto {

    private String id;
    private String email;
    private String name;
    private String body;
}
