package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.CategoryRequestDto;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.repository.CategoryRepository;
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
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldCreateCategory() throws Exception {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Test Category");

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        Category category = categoryRequestDto.toModel(repository);

        when(repository.save(category)).thenReturn(category);

        var response = mockMvc.perform(post("/api/v1/categories")
                .content(mapper.writeValueAsString(category))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals("Test Category", mapper
                .readValue(response.getContentAsString(), new TypeReference<Category>() {}).getName());
    }

//    @Test
//    public void shouldListCategories() throws Exception {
//        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Test Category2");
//        Category category = categoryRequestDto.toModel(repository);
//        repository.save(category);
//
//        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
//
//        doReturn(Lists.newArrayList(category)).when(repository).findAll();
//
//        var response = mockMvc.perform(get("/api/v1/categories"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertEquals(1, mapper
//                .readValue(response.getContentAsString(), new TypeReference<List<Category>>() {}).size());
//    }

}
