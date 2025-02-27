package dev.ryan.movies.controllers;

import dev.ryan.movies.data.User;
import dev.ryan.movies.services.JWTService;
import dev.ryan.movies.services.MovieService;
import dev.ryan.movies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<Optional<User>>(userService.findUserByEmail(email), HttpStatus.OK);
    }

    //Registering users.
    @PostMapping("/register")
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
    public ResponseEntity<Optional<User>> loginUser(@RequestBody Map<String, String> payload){
        //User found
        Optional<User> user = userService.findUserByEmail(payload.get("email"));
        if(user.isPresent()) {
            if (passwordEncoder.matches(payload.get("password"), user.get().getPassword())) {
                String authToken = jwtService.generateToken(user.get().getEmail());

                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.add("Authorization", "Bearer "+authToken);
                headers.setAccessControlExposeHeaders(Arrays.asList("Authorization"));

                return new ResponseEntity<>(user, headers, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
