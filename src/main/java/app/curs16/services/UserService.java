package app.curs16.services;

import app.curs16.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    List<User> users = new ArrayList<>();
    public boolean addUser(User user){
        if(users.stream().anyMatch(u->u.getEmail().equals(user.getEmail()))){
            return false;
        }
        users.add(user);
        return true;
    }
    public Optional<User> findUserByEmailAndPassword(String email, String password){
        return users.stream().filter(u->u.getPassword().equals(password) && u.getEmail().equals(email)).findFirst();
    }
    public boolean userExists(String email){
        return users.stream().anyMatch(u -> u.getEmail().equals(email));
    }
}
