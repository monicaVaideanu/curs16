package app.curs16.utils;

import app.curs16.models.Login;
import app.curs16.models.User;

public class DummyData {
    public static User mockedUser(){
        return new User("mihai", "andrei", "test@example.com", "password");
    }
    public static Login mockedLogin(){
        return new Login("test@example.com", "password");
    }
}
