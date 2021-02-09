package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.utils.builder.UserBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    public void testConstructor() {
        User actualUser = new User("jane.doe@example.org", "iloveyou");

        assertEquals("iloveyou", actualUser.getPassword());
        assertEquals("jane.doe@example.org", actualUser.getEmail());
    }

    @Test
    public void testToString() {
        assertEquals("Usuário{id:null, e-mail:'null', Cadastrado em:null}", (new User()).toString());
    }

    @Test
    public void testEquals() {
        User user = new User();

        assertEquals(new User(), user);
    }

    @Test
    public void testEquals3() {
        User user = new User();

        assertNotEquals(new User("jane.doe@example.org", "iloveyou"), user);
    }

    @Test
    public void testEquals4() {
        User user = new User("jane.doe@example.org", "iloveyou");

        assertNotEquals(new User(), user);
    }

    @Test
    public void testEquals5() {
        User user = new User("jane.doe@example.org", "iloveyou");

        assertEquals(new User("jane.doe@example.org", "iloveyou"), user);
    }

    @Test
    public void testEquals6() {
        User user = new User();

        assertNotEquals(new User(null, "iloveyou"), user);
    }

    @Test
    public void testHashCode() {
        assertEquals(961, (new User()).hashCode());
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
