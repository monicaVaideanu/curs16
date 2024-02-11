package app.curs16.controller;

import app.curs16.models.Login;
import app.curs16.models.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    private final List<User> users = new ArrayList<>();
    private final List<Login> usersLoggedIn = new ArrayList<>();

    @PostMapping("/register")
    public ResponseEntity<String> handleRegister(@Valid @RequestBody User user) {
        if (users.stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.");
        }
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Login loginBody) {
        if (users.stream().noneMatch(u -> u.getEmail().equals(loginBody.getEmail())
                && u.getPassword().equals(loginBody.getPassword()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user was not found.");
        }
        if (usersLoggedIn.stream().anyMatch(u -> u.getEmail().equals(loginBody.getEmail())
                && u.getPassword().equals(loginBody.getPassword()))) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("This user is already logged in.");
        }
        usersLoggedIn.add(loginBody);
        return ResponseEntity.status(HttpStatus.OK).body("Logged in successfully.");
    }

    @PostMapping("/logout/{email}")
    public ResponseEntity<String> logoutUser(@PathVariable String email){
        if(users.stream().noneMatch(u -> u.getEmail().equals(email))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user was not found.");
        }
        if(usersLoggedIn.stream().noneMatch(u -> u.getEmail().equals(email))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user was not logged in.");
        }
        usersLoggedIn.removeIf(u->u.getEmail().equals(email));
        return ResponseEntity.status(HttpStatus.OK).body("User logged out.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
