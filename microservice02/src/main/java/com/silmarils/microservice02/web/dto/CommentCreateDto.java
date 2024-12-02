package com.silmarils.microservice02.web.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CommentCreateDto {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max = 50)
    private String name;
    @NotBlank
    @Size(min = 4, max = 150)
    private String body;
    @NotBlank
    private String postId;

}
