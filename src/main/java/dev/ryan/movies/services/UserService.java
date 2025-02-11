package dev.ryan.movies.services;

import dev.ryan.movies.data.User;
import dev.ryan.movies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public User createUser(String username, String password, String email, String birthDate){
        User user = userRepository.insert(new User(username, password, email, birthDate));

        return userRepository.save(user);
    }
}
