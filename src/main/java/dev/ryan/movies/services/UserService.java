package dev.ryan.movies.services;

import dev.ryan.movies.data.Movie;
import dev.ryan.movies.data.User;
import dev.ryan.movies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public Optional<List<String>> findFavoriteMoviePosters(String email){
        Optional<User> user = userRepository.findUserByEmail(email);

        if(user.isPresent()){
            List<Movie> favoriteMovies = user.get().getFavoriteMovies();

            List<String> posterLinks = new ArrayList<>();

            for (Movie movie : favoriteMovies){
                posterLinks.add(movie.getPoster());
            }

            return Optional.of(posterLinks);
        }else{
            return Optional.empty();
        }
    }

    public User createUser(String email, String password, String birthDate){
        User user = userRepository.insert(new User(email, password, birthDate));

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }
}
