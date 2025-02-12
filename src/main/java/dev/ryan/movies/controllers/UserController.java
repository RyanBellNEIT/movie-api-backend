package dev.ryan.movies.controllers;

import dev.ryan.movies.data.User;
import dev.ryan.movies.services.MovieService;
import dev.ryan.movies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<Optional<User>>(userService.findUserByEmail(email), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody Map<String, Object> payload){
        return new ResponseEntity<>(userService.createUser(String.valueOf(payload.get("username")),
                String.valueOf(payload.get("password")), String.valueOf(payload.get("email")),
                String.valueOf(payload.get("birthDate"))), HttpStatus.CREATED);
    }
}
