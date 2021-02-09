package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.utils.builder.UserBuilder;
import br.com.zup.mercadolivre.utils.builder.UserRequestDtoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserRequestDtoTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void testToModel() {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user@email.com")
                .withPassword("pass12345")
                .build();

        var testUser = userRequestDto.toModel(this.encoder);
        var newUser = new UserBuilder()
                .withEmail("user@email.com")
                .withPassword("pass12345")
                .build();

        assertNotNull(testUser);
        assertEquals(testUser.hashCode(), newUser.hashCode());
    }

    @Test
    public void testToModel2() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto("jane.doe@example.org", "iloveyou");

        // Act and Assert
        assertEquals("jane.doe@example.org", userRequestDto.toModel(this.passwordEncoder).getEmail());
    }

    @Test
    public void shouldReturnValidationErrorIfEmailIsEmpty() {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("")
                .withPassword("pass12345")
                .build();

        assertEquals(1, validator.validate(userRequestDto).size());
        assertNotNull(userRequestDto);
    }

    @Test
    public void shouldReturnValidationErrorIfEmailIsInvalid() {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user.email.com")
                .withPassword("pass12345")
                .build();

        assertEquals(1, validator.validate(userRequestDto).size());
        assertNotNull(userRequestDto);
    }

    @Test
    public void shouldReturnValidationErrorIfPasswordIsEmpty() {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user24@email.com")
                .withPassword("")
                .build();

        assertEquals(2, validator.validate(userRequestDto).size());
        assertNotNull(userRequestDto);
    }

    @Test
    public void shouldReturnValidationErrorIfPasswordLengthIsTooShort() {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user23@email.com")
                .withPassword("pass1")
                .build();

        var edu = validator.validate(userRequestDto);
        assertEquals(1, validator.validate(userRequestDto).size());
        assertNotNull(userRequestDto);
    }
}

