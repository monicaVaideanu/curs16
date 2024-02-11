package app.curs16.controller;

import app.curs16.models.Login;
import app.curs16.models.User;
import app.curs16.services.AuthentificationService;
import app.curs16.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthentificationService authService;
    @PostMapping("/register")
    public ResponseEntity<String> handleRegister(@Valid @RequestBody User user) {
        if (!userService.addUser(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User added.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Login loginBody) {
        if (userService.findUserByEmailAndPassword(loginBody.getEmail(), loginBody.getPassword()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user was not found.");
        }
        if (authService.isLoggedIn(loginBody.getEmail())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("This user is already logged in.");
        }
        authService.addLoggedInUser(loginBody);
        return ResponseEntity.status(HttpStatus.OK).body("Logged in successfully.");
    }

    @PostMapping("/logout/{email}")
    public ResponseEntity<String> logoutUser(@PathVariable String email) {
        if (!userService.userExists(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user was not found.");
        }
        if (!authService.isLoggedIn(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user was not logged in.");
        }
        authService.logoutUser(email);
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
