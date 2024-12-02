package com.silmarils.microservice02.entities;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "tb_post")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Post implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Integer userId;
    @Size(min = 5, max = 50)
    private String title;
    @Size(min = 1, max = 500)
    private String body;
    @DBRef
    private List<Comment> comments = new ArrayList<>();

    public Post(String id, Integer userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return Objects.equals(this.getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }


}
