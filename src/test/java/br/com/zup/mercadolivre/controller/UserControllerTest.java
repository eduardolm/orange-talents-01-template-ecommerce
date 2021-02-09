package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.UserRequestDto;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.UserRepository;
import br.com.zup.mercadolivre.utils.builder.UserRequestDtoBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void shouldCreateUser() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user2@email.com")
                .withPassword("pass1234")
                .build();

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        User user = userRequestDto.toModel(encoder);

        when(repository.save(user)).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                .content(mapper.writeValueAsString(userRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();
    }

    @Test
    public void shouldNotCreateUserWhenEmailIsNull() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail(null)
                .withPassword("pass1234")
                .build();

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        User user = userRequestDto.toModel(encoder);

        when(repository.save(user)).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                .content(mapper.writeValueAsString(userRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    public void shouldNotCreateUserWhenPasswordIsNull() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user2@email.com")
                .withPassword("")
                .build();

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        User user = userRequestDto.toModel(encoder);

        when(repository.save(user)).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                .content(mapper.writeValueAsString(userRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertTrue(repository.findAll().isEmpty());
    }
}
