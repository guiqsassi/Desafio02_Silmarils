package com.silmarils.microservice02.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private String id;
    private Integer userId;
    private String title;
    private String body;
}
