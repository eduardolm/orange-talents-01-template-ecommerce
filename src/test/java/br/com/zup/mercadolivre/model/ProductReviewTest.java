package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.utils.builder.ProductBuilder;
import org.assertj.core.util.Lists;
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
public class ProductReviewTest {

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
        Category category = new Category("Celulares & Tablets");
        category.setId(1L);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        this.category = category;

        // Create Characteristics
        this.characteristics = Lists.newArrayList(
                new CharacteristicsRequestDto("key", "value"),
                new CharacteristicsRequestDto("key2", "value2"),
                new CharacteristicsRequestDto("key3", "value3")
        );

        // Create product
        this.product = new ProductBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(new Category("Celulares & Tablets"))
                .withCharacteristics(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();
    }

    @Test
    public void shouldCreateNewProductReviewInstance() {
        ProductReview productReview = new ProductReview();

        assertTrue(productReview instanceof ProductReview);
    }

    @Test
    public void shouldOverloadedConstructorCreateProductReviewInstance() {
        ProductReview productReview = new ProductReview(3, "Bom produto", "Produto atende as " +
                "expectativas", product, user);

        assertTrue(productReview instanceof ProductReview);
    }

    @Test
    public void shouldGettersWorkAsExpected() {
        ProductReview productReview = new ProductReview(3, "Bom produto", "Produto atende as " +
                "expectativas", product, user);

        assertEquals(3, productReview.getGrade());
        assertEquals("Bom produto", productReview.getTitle());
        assertEquals("Produto atende as expectativas", productReview.getDescription());
    }
}
