package app.curs16.controller;

import app.curs16.models.Login;
import app.curs16.models.User;
import app.curs16.services.AuthentificationService;
import app.curs16.services.UserService;
import app.curs16.utils.DummyData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HomeControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthentificationService authService;

    @InjectMocks
    private HomeController homeController;
    User newUser = DummyData.mockedUser();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenExistingUser_whenHandleRegister_thenBadRequestReturned() {
        User existingUser = DummyData.mockedUser();
        when(userService.addUser(existingUser)).thenReturn(false);
        ResponseEntity<String> response = homeController.handleRegister(existingUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists.", response.getBody());
    }

    @Test
    void givenNewUser_whenHandleRegister_thenUserAddedSuccessfully() {
        when(userService.addUser(newUser)).thenReturn(true);
        ResponseEntity<String> response = homeController.handleRegister(newUser);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User added.", response.getBody());
    }

    @Test
    void givenUserNotLoggedIn_whenLogoutUser_thenBadRequestReturned() {
        String email = "test@example.com";
        when(userService.userExists(email)).thenReturn(true);
        when(authService.isLoggedIn(email)).thenReturn(false);
        ResponseEntity<String> response = homeController.logoutUser(email);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("This user was not logged in.", response.getBody());
    }
    @Test
    void givenUserNotFound_whenLoginUser_thenBadRequestReturned() {
        // Given
        Login login = new Login("test@example.com", "password");

        // When
        when(userService.findUserByEmailAndPassword(login.getEmail(), login.getPassword())).thenReturn(Optional.empty());
        ResponseEntity<String> response = homeController.loginUser(login);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("This user was not found.", response.getBody());
    }

    @Test
    void givenUserAlreadyLoggedIn_whenLoginUser_thenAcceptedReturned() {
        // Given
        Login login = new Login("test@example.com", "password");

        // When
        when(userService.findUserByEmailAndPassword(login.getEmail(), login.getPassword())).thenReturn(Optional.of(new User()));
        when(authService.isLoggedIn(login.getEmail())).thenReturn(true);
        ResponseEntity<String> response = homeController.loginUser(login);

        // Then
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("This user is already logged in.", response.getBody());
    }

    @Test
    void givenValidCredentials_whenLoginUser_thenLoggedInSuccessfully() {
        // Given
        Login login = new Login("test@example.com", "password");

        // When
        when(userService.findUserByEmailAndPassword(login.getEmail(), login.getPassword())).thenReturn(Optional.of(new User()));
        when(authService.isLoggedIn(login.getEmail())).thenReturn(false);
        ResponseEntity<String> response = homeController.loginUser(login);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged in successfully.", response.getBody());
    }


}
