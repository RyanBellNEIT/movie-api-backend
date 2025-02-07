package dev.ryan.movies.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public User(String username, String password, Long birthDate){
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
    }

    @Id
    private ObjectId id;
    private String username;
    private String password;
    private Long birthDate;
    @DocumentReference
    private List<Movie> favoriteMovies;
    @DocumentReference
    private List<Review> postedReviews;
}
