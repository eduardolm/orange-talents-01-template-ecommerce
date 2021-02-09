package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserDtoTest {
    @Test
    public void testConstructor() {
        UserDto actualUserDto = new UserDto(new User());

        assertNull(actualUserDto.getEmail());
        assertNull(actualUserDto.getId());
    }

    @Test
    public void testConstructor2() {
        UserDto actualUserDto = new UserDto(new User("Email", "iloveyou"));

        assertEquals("Email", actualUserDto.getEmail());
        assertNull(actualUserDto.getId());
    }

    @Test
    public void shouldCreateUserDtoInstance() {
        UserDto actualUserDto = new UserDto(new User());

        assertNull(actualUserDto.getEmail());
        assertNull(actualUserDto.getId());
        assertTrue(actualUserDto instanceof UserDto);
    }

    @Test
    public void shouldGetEmail() {
        UserDto actualUserDto = new UserDto(new User("Email", "iloveyou"));

        assertEquals("Email", actualUserDto.getEmail());
        assertNull(actualUserDto.getId());
    }
}

