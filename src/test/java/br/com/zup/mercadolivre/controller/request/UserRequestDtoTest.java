package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.User;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserRequestDtoTest {

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

        assertTrue(testUser instanceof User);
        assertEquals(testUser.hashCode(), newUser.hashCode());
    }

    @Test
    public void shouldReturnValidationErrorIfEmailIsEmpty() {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("")
                .withPassword("pass12345")
                .build();

        assertEquals(1, validator.validate(userRequestDto).size());
        assertTrue(userRequestDto instanceof UserRequestDto);
    }

    @Test
    public void shouldReturnValidationErrorIfEmailIsInvalid() {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user.email.com")
                .withPassword("pass12345")
                .build();

        assertEquals(1, validator.validate(userRequestDto).size());
        assertTrue(userRequestDto instanceof UserRequestDto);
    }

    @Test
    public void shouldReturnValidationErrorIfPasswordIsEmpty() {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user24@email.com")
                .withPassword("")
                .build();

        assertEquals(2, validator.validate(userRequestDto).size());
        assertTrue(userRequestDto instanceof UserRequestDto);
    }

    @Test
    public void shouldReturnValidationErrorIfPasswordLengthIsTooShort() {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user23@email.com")
                .withPassword("pass1")
                .build();

        var edu = validator.validate(userRequestDto);
        assertEquals(1, validator.validate(userRequestDto).size());
        assertTrue(userRequestDto instanceof UserRequestDto);
    }
}

