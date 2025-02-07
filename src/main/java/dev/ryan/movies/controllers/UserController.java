package dev.ryan.movies.controllers;

import dev.ryan.movies.data.User;
import dev.ryan.movies.services.MovieService;
import dev.ryan.movies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String username){
        return new ResponseEntity<Optional<User>>(userService.findUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody Map<String, Object> payload){
        return new ResponseEntity<>(userService.createUser(String.valueOf(payload.get("username")),
                String.valueOf(payload.get("password")),
                (Long.parseLong(payload.get("birthDate").toString()))), HttpStatus.CREATED);
    }
}
