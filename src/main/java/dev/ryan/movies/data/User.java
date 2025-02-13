package dev.ryan.movies.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public User(String email, String password, String birthDate){
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.favoriteMovies = Collections.emptyList();
        this.postedReviews = Collections.emptyList();
    }

    @Id
    private ObjectId id;
    private String email;
    private String password;
    private String birthDate;
    @DocumentReference
    private List<Movie> favoriteMovies;
    @DocumentReference
    private List<Review> postedReviews;
}
