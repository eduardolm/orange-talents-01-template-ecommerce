package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.User;
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
public class ProductDtoTest {

    @MockBean
    private CategoryRepository categoryRepository;

    private User user;
    private Category category;
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
    public void shouldStandardEmptyConstructorCreateANewInstance() {
        ProductDto productDto = new ProductDto();

        assertTrue(productDto instanceof ProductDto);
    }

    @Test
    public void shouldTwoEmptyInstancesBeEqual() {
        ProductDto productDto = new ProductDto();
        ProductDto productDto2 = new ProductDto();

        assertEquals(productDto, productDto2);
        assertEquals(productDto2, productDto);
        assertEquals(productDto.hashCode(), productDto2.hashCode());
        assertEquals(productDto2.hashCode(), productDto.hashCode());
    }

    @Test
    public void shouldTwoInstancesWithOneDifferentPropertyBeNotEqual() {
        ProductDto productDto = new ProductDto(new ProductBuilder()
                .withName("Test product")
                .withQuantity(10)
                .withDescription("Description")
                .withPrice(BigDecimal.TEN)
                .withCategory(this.category)
                .withProductOwner(this.user)
                .withCharacteristics(this.characteristics)
                .build());

        ProductDto productDto2 = new ProductDto(new ProductBuilder()
                .withName("Test product1")
                .withQuantity(10)
                .withDescription("Description")
                .withPrice(BigDecimal.TEN)
                .withCategory(this.category)
                .withProductOwner(this.user)
                .withCharacteristics(this.characteristics)
                .build());

        assertNotEquals(productDto, productDto2);
        assertNotEquals(productDto.hashCode(), productDto2.hashCode());

    }
}
