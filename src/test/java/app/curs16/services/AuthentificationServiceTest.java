package app.curs16.services;

import app.curs16.models.Login;
import app.curs16.utils.DummyData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AuthentificationServiceTest {
    @InjectMocks
    AuthentificationService authService;
    Login login = DummyData.mockedLogin();

    @Test
    void givenLogin_WhenCallingLoginUser_ThenReturnTrue() {
        assertTrue(authService.loginUser(login));
    }

    @Test
    void givenLogin_WhenCallingAddLoggedInUser_ThenReturnTrue() {
        authService.addLoggedInUser(login);
        assertTrue(authService.isLoggedIn("test@example.com"));
    }

    @Test
    void givenLogin_WhenCallingLogoutUser_ThenLogoutUser() {
        authService.addLoggedInUser(login);
        authService.logoutUser("test@example.com");
        assertTrue(authService.loggedIn.isEmpty());
        assertFalse(authService.isLoggedIn("test@example.com"));
    }
    @Test
    void givenLoggedInUser_whenLoginUser_thenReturnsFalse() {
        authService.addLoggedInUser(login);
        boolean result = authService.loginUser(login);
        assertFalse(result);
    }

    @Test
    void givenNotLoggedInUser_whenLoginUser_thenReturnsTrue() {
        boolean result = authService.loginUser(login);
        assertTrue(result);
    }
}
