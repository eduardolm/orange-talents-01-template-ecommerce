package br.com.zup.mercadolivre.controller.request;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;

public class LoginInputDtoTest {
    @Test
    public void testSetEmail() {
        LoginInputDto loginInputDto = new LoginInputDto();

        loginInputDto.setEmail("jane.doe@example.org");

        assertEquals("jane.doe@example.org", loginInputDto.getEmail());
    }

    @Test
    public void testSetEmail2() {
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
    public void testSetPassword2() {
        LoginInputDto loginInputDto = new LoginInputDto();

        loginInputDto.setPassword("iloveyou");

        assertEquals("iloveyou", loginInputDto.getPassword());
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

    @Test
    public void testBuild() {
        UsernamePasswordAuthenticationToken actualBuildResult = (new LoginInputDto()).build();

        assertNull(actualBuildResult.getCredentials());
        assertNull(actualBuildResult.getPrincipal());
        assertFalse(actualBuildResult.isAuthenticated());
        assertEquals("UsernamePasswordAuthenticationToken [Principal=null, Credentials=[PROTECTED], Authenticated=false,"
                + " Details=null, Granted Authorities=[]]", actualBuildResult.toString());
    }
}

