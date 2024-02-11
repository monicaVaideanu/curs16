package app.curs16.services;

import app.curs16.models.Login;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthentificationService {
    List<Login> loggedIn = new ArrayList<>();

    public boolean loginUser(Login loginBody) {
        return loggedIn.stream().noneMatch(u -> u.getEmail().equals(loginBody.getEmail())
                && u.getPassword().equals(loginBody.getPassword()));
    }

    public void addLoggedInUser(Login loginBody) {
        loggedIn.add(loginBody);
    }

    public boolean logoutUser(String email) {
        return loggedIn.removeIf(u -> u.getEmail().equals(email));
    }

    public boolean isLoggedIn(String email) {
        return loggedIn.stream().anyMatch(u -> u.getEmail().equals(email));
    }
}
