package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.utils.builder.ProductBuilder;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @AfterEach
    public void rollbackDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    public void shouldCreateNewProductReviewInstance() {
        ProductReview productReview = new ProductReview();

        assertTrue(true);
    }

    @Test
    public void shouldOverloadedConstructorCreateProductReviewInstance() {
        ProductReview productReview = new ProductReview(3, "Bom produto", "Produto atende as " +
                "expectativas", product, user);

        assertTrue(true);
    }

    @Test
    public void testToString() {
        assertEquals("ProductReview{Id:null, Nota:null, Titulo:'null', Descrição:'null', Produto:null, Cliente:null}",
                (new ProductReview()).toString());
    }

    @Test
    public void testEquals() {
        assertNotEquals((new ProductReview()), "o");
    }

    @Test
    public void testEquals2() {
        ProductReview productReview = new ProductReview();

        assertEquals(new ProductReview(), productReview);
    }

    @Test
    public void testEquals3() {
        ProductReview productReview = new ProductReview();
        Product product = new Product();

        assertNotEquals(new ProductReview(1, "Dr", "The characteristics of someone or something", product, new User()), productReview);
    }

    @Test
    public void testEquals4() {
        Product product = new Product();
        ProductReview productReview = new ProductReview(1, "Dr", "The characteristics of someone or something", product,
                new User());

        assertNotEquals(new ProductReview(), productReview);
    }

    @Test
    public void testEquals5() {
        ProductReview productReview = new ProductReview(1, "Dr", "The characteristics of someone or something", null,
                new User());
        Product product = new Product();

        assertNotEquals(new ProductReview(1, "Dr", "The characteristics of someone or something", product, new User()), productReview);
    }

    @Test
    public void testEquals6() {
        ProductReview productReview = new ProductReview();
        Product product = new Product();

        assertNotEquals(new ProductReview(1, null, "The characteristics of someone or something", product, new User()), productReview);
    }

    @Test
    public void testEquals7() {
        Product product = new Product();
        ProductReview productReview = new ProductReview(1, null, "The characteristics of someone or something", product,
                new User());

        assertNotEquals(new ProductReview(), productReview);
    }

    @Test
    public void testHashCode() {
        assertEquals(0, (new ProductReview()).hashCode());
        assertEquals(-660048912,
                (new ProductReview(1, "Dr", "The characteristics of someone or something", null, new User())).hashCode());
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
