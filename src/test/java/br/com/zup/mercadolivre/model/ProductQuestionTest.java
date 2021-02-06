package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.utils.builder.ProductRequestDtoBuilder;
import br.com.zup.mercadolivre.utils.builder.QuestionRequestDtoBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProductQuestionTest {

    @MockBean
    private CategoryRepository categoryRepository;

    private User user;
    private Category category;
    private Product product;
    private Collection<CharacteristicsRequestDto> characteristics;

    @BeforeEach
    public void setup() {
        // Create User
        this.user = new User("test@email.com", "pass1234");

        // Create Category
        this.category = new Category("Celulares & Tablets");
        this.category.setId(1L);
        when(categoryRepository.findById(this.category.getId())).thenReturn(Optional.of(this.category));


        // Create Characteristics
        this.characteristics = Lists.newArrayList(
                new CharacteristicsRequestDto("key", "value"),
                new CharacteristicsRequestDto("key2", "value2"),
                new CharacteristicsRequestDto("key3", "value3")
        );

        // Create Product
        this.product = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build().toModel(categoryRepository, this.user);
    }

    @AfterEach
    public void rollbackDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    public void shouldQuestionRequestDtoCreateAQuestionInstance() {
        ProductQuestion productQuestion = new QuestionRequestDtoBuilder()
                .withTitle("Test title")
                .build().toModel(this.user, this.product);

        assertTrue(productQuestion instanceof ProductQuestion);
    }

    @Test
    public void shouldGettersReturnAsExpectedQuestionProperties() {
        ProductQuestion productQuestion = new QuestionRequestDtoBuilder()
                .withTitle("Test title")
                .build().toModel(this.user, this.product);

        assertEquals("Test title", productQuestion.getTitle());
        assertEquals(user.getEmail(), productQuestion.getInterested().getEmail());
        assertEquals(product.getName(), productQuestion.getProduct().getName());
    }
}
