package dev.ryan.movies.controllers;

import dev.ryan.movies.data.User;
import dev.ryan.movies.services.MovieService;
import dev.ryan.movies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<Optional<User>>(userService.findUserByEmail(email), HttpStatus.OK);
    }

    //Registering users.
    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody Map<String, Object> payload){
        if(userService.findUserByEmail(String.valueOf(payload.get("email"))).isEmpty()){
            return new ResponseEntity<>(userService.createUser(String.valueOf(payload.get("email")),
                    String.valueOf(payload.get("password")),
                    String.valueOf(payload.get("birthDate"))), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<Optional<User>> loginUser(Map<String, String> payload){
        //User found
        Optional<User> user = userService.findUserByEmail(payload.get("email"));
        if(user.isPresent()){
            if (passwordEncoder.matches(payload.get("password"), user.get().getPassword())){
                return new ResponseEntity<>(user, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
