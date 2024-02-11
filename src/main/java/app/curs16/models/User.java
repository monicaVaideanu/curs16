package app.curs16.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull(message = "firstName must be not-null")
    @NotBlank(message = "firstName must be a non-empty string")
    private String firstName;
    @NotNull(message = "lastName must be not-null")
    @NotBlank(message = "lastName must be a non-empty string")
    private String lastName;
    @NotNull(message = "email must be not-null")
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "password must have digit + lowercase + uppercase + punctuation + symbol")
    private String password;
}
