package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.UserRequestDto;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.UserRepository;
import br.com.zup.mercadolivre.utils.builder.UserBuilder;
import br.com.zup.mercadolivre.utils.builder.UserRequestDtoBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
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
    public void shouldListUsers() throws Exception {
        User user = new UserBuilder()
                .withEmail("user@mail.com")
                .withPassword("pass1234")
                .build();

        repository.save(user);

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

        doReturn(Lists.newArrayList(user)).when(repository).findAll();

        var response = mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals("user@mail.com", mapper
                .readValue(response.getContentAsString(), new TypeReference<List<User>>() {}).get(0).getEmail());
        assertEquals("pass1234", mapper
                .readValue(response.getContentAsString(), new TypeReference<List<User>>() {}).get(0).getPassword());
    }

    @Test
    public void shouldCreateUser() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDtoBuilder()
                .withEmail("user2@email.com")
                .withPassword("pass1234")
                .build();

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        User user = userRequestDto.toModel(encoder);

        when(repository.save(user)).thenReturn(user);

        var response = mockMvc.perform(post("/api/v1/users")
                .content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();
    }
}
