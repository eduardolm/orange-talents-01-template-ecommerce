package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.utils.builder.UserBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void shouldCreateANewUserInstance() {
        User user = new UserBuilder()
                .withEmail("user@mail.com")
                .withPassword("pass1234")
                .build();

        assertEquals("user@mail.com", user.getEmail());
    }

    @Test
    public void shouldTwoUserInstancesNotBeEqualExceptIfEmpty() {

        User user1 = new UserBuilder()
                .withEmail("user@mail.com")
                .withPassword("pass1234")
                .build();

        User user2 = new UserBuilder()
                .withEmail("user2@mail.com")
                .withPassword("pass1234")
                .build();

        User user3 = new User();
        User user4 = new User();

        assertNotEquals(user1.hashCode(), user2.hashCode());
        assertEquals(user3.hashCode(), user4.hashCode());
        assertNotEquals(user3.hashCode(), user1.hashCode());
    }

    @Test
    public void shouldTwoIdenticalStudentsBeEqual() {
        User user1 = new UserBuilder()
                .withEmail("user@mail.com")
                .withPassword("pass1234")
                .build();

        User user2 = new UserBuilder()
                .withEmail("user@mail.com")
                .withPassword("pass1234")
                .build();

        assertEquals(user1.hashCode(), user2.hashCode());
        assertEquals(user1.getEmail(), user2.getEmail());
    }

    @Test
    public void shouldUserGetterWorkCorrectly() {
        User user = new UserBuilder()
                .withEmail("user@mail.com")
                .withPassword("pass1234")
                .build();

        assertEquals("user@mail.com", user.getEmail());
        assertEquals("pass1234", user.getPassword());
    }
}
