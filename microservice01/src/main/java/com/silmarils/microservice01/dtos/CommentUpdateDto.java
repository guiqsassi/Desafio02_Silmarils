package com.silmarils.microservice01.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CommentUpdateDto {

    @NotBlank
    @Size(min = 4, max = 50)
    private String name;
    @NotBlank
    @Size(min = 4, max = 150)
    private String body;


}
