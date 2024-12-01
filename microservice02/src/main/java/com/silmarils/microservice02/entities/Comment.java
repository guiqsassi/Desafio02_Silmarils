package com.silmarils.microservice02.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {
    @Id
    private String id;
    private String email;
    private String name;
    private String body;
    private String postId;
}
