package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.config.security.TokenManager;
import br.com.zup.mercadolivre.controller.request.LoginInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserAuthenticationController.class})
@ExtendWith(SpringExtension.class)
public class UserAuthenticationControllerTest {

    @MockBean
    private AuthenticationManager authManager;

    @MockBean
    private TokenManager tokenManager;

    @Autowired
    private UserAuthenticationController userAuthenticationController;

    @Test
    public void testAuthenticate() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new LoginInputDto()));

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userAuthenticationController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
}

