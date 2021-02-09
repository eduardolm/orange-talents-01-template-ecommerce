package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.controller.request.ProductRequestDto;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.utils.builder.CategoryBuilder;
import br.com.zup.mercadolivre.utils.builder.ProductRequestDtoBuilder;
import br.com.zup.mercadolivre.utils.builder.UserBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository repository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper mapper;

    private Category category;

    @BeforeEach
    public void setup() {
        Category category = new Category("Celulares & Tablets");
        category.setId(1L);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        this.category = category;
    }

    @AfterEach
    public void rollbackDatabase() {
        repository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user@email.com", password = "pass1234")
    public void shouldListProducts() throws Exception {

        User user = new UserBuilder().withEmail("user2@email.com").withPassword("pass1234").build();

        ProductRequestDto productRequestDto = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(category.getId())
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        Product product = productRequestDto.toModel(categoryRepository, user);
        repository.save(product);
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

        doReturn(Lists.newArrayList(product)).when(repository).findAll();

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    // TODO: Corrigir este teste de criação do produto

    @Test
    @WithMockUser(username = "user@email.com", password = "pass1234")
    public void shouldCreateProduct() throws Exception {
        User user = new UserBuilder().withEmail("user2@email.com").withPassword("pass1234").build();
        ProductRequestDto productRequestDto = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(category.getId())
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        Product product = productRequestDto.toModel(categoryRepository, user);

        when(repository.save(product)).thenReturn(product);

        var response = mockMvc.perform(post("/api/v1/products")
                .content(mapper.writeValueAsString(productRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
