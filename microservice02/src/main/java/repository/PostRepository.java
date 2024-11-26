package repository;

import com.silmarils.microservice02.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post,Long> {
}
