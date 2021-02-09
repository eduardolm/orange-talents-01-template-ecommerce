package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.CategoryRequestDto;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser(username = "user2@email.com", password = "pass1234")
    public void shouldCreateCategory() throws Exception {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Test Category1");

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        Category category = categoryRequestDto.toModel(repository);

        when(repository.save(category)).thenReturn(category);

        var response = mockMvc.perform(post("/api/v1/categories")
                .content(mapper.writeValueAsString(categoryRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals("Test Category1", mapper
                .readValue(response.getContentAsString(), Category.class).getName());
    }
}
