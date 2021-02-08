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
public class ProductImageTest {

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
    }

    @AfterEach
    public void rollbackDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    public void shouldCreateProductImageInstance() {
        this.product = new ProductBuilder()
                .withName("Test product")
                .withQuantity(10)
                .withDescription("Description")
                .withPrice(BigDecimal.TEN)
                .withCategory(this.category)
                .withProductOwner(this.user)
                .withCharacteristics(this.characteristics)
                .build();

        ProductImage productImage = new ProductImage(product, "https://localhost.com");

        assertTrue(productImage instanceof ProductImage);
        assertEquals("https://localhost.com", productImage.getLink());
    }

    @Test
    public void testToString() {
        // Arrange, Act and Assert
        assertEquals("ProductImage{id=null, link='null'}", (new ProductImage()).toString());
    }

    @Test
    public void testEquals() {
        // Arrange, Act and Assert
        assertFalse((new ProductImage()).equals("obj"));
    }

    @Test
    public void testEquals2() {
        // Arrange
        ProductImage productImage = new ProductImage();

        // Act and Assert
        assertTrue(productImage.equals(new ProductImage()));
    }

    @Test
    public void testEquals3() {
        // Arrange
        ProductImage productImage = new ProductImage();

        // Act and Assert
        assertFalse(productImage.equals(new ProductImage(new Product(), "Link")));
    }

    @Test
    public void testHashCode() {
        // Arrange, Act and Assert
        assertEquals(0, (new ProductImage()).hashCode());
        assertEquals(2368538, (new ProductImage(null, "Link")).hashCode());
    }
}

