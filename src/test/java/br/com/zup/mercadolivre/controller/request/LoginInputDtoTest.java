package br.com.zup.mercadolivre.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginInputDtoTest {
    @Test
    public void testSetEmail() {
        LoginInputDto loginInputDto = new LoginInputDto();

        loginInputDto.setEmail("jane.doe@example.org");

        assertEquals("jane.doe@example.org", loginInputDto.getEmail());
    }

    @Test
    public void testSetPassword() {
        LoginInputDto loginInputDto = new LoginInputDto();

        loginInputDto.setPassword("testPass123456");

        assertEquals("testPass123456", loginInputDto.getPassword());
    }

    @Test
    public void shouldBuildUsernamePasswordAuthenticationTokenInstance() {
        UsernamePasswordAuthenticationToken actualBuildResult = (new LoginInputDto()).build();

        assertNull(actualBuildResult.getCredentials());
        assertNull(actualBuildResult.getPrincipal());
        assertFalse(actualBuildResult.isAuthenticated());
        assertEquals("UsernamePasswordAuthenticationToken [Principal=null, Credentials=[PROTECTED], Authenticated=false,"
                + " Details=null, Granted Authorities=[]]", actualBuildResult.toString());
    }
}

