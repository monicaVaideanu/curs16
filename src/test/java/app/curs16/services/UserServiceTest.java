package app.curs16.services;

import app.curs16.models.User;
import app.curs16.utils.DummyData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    User user = DummyData.mockedUser();
    @Test
    void givenValidUser_WhenCallingAddUser_ThenAddUser() {
        Assertions.assertTrue(userService.addUser(user));
    }

    @Test
    void givenUser_WhenCallingFindUserByEmailAndPassword_ThenReturnTrue() {
        userService.addUser(user);
        Assertions.assertTrue(userService.findUserByEmailAndPassword("test@example.com", "password").isPresent());
    }

    @Test
    void givenUser_whenCallingUserExists_ThenReturnTrue() {
        userService.addUser(user);
        Assertions.assertTrue(userService.userExists("test@example.com"));
    }
}
