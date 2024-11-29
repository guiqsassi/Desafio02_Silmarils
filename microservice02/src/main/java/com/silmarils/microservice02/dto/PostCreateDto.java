package com.silmarils.microservice02.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
 @Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class PostCreateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer userId;
    @NotBlank
    @Size(min = 5, max = 50)
    private String title;
    @NotBlank
    @Size(min = 5, max = 50)
    private String body;

 }
