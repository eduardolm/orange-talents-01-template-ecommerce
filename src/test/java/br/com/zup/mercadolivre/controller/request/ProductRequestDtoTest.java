package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.utils.builder.CategoryBuilder;
import br.com.zup.mercadolivre.utils.builder.ProductRequestDtoBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProductRequestDtoTest {

    @Autowired
    private Validator validator;

    @MockBean
    private ProductRepository repository;

    @MockBean
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        Category category = new CategoryBuilder().withName("Celulares & Tablets").build();
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));
    }

    @AfterEach
    public void rollbackDatabase() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateNewInstanceOfProductRequestDto() {
        ProductRequestDto productRequestDto = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(2L)
                .build();

        assertTrue(productRequestDto instanceof ProductRequestDto);
    }

    @Test
    public void shouldToModelCreateProductInstance() {

        Product product = (new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .build()).toModel(categoryRepository);

        assertTrue(product instanceof Product);
        assertEquals("Galaxy S20", product.getName());
    }

    @Test
    public void shouldReturnErrorWhenNameIsBlank() {
        ProductRequestDto product = new ProductRequestDtoBuilder()
                .withName("")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .build();

        assertEquals(1, validator.validate(product).size());
    }

    @Test
    public void shouldBeOkWhenQuantityIsNull() {
        ProductRequestDto product = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(0)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .build();

        assertTrue(product instanceof ProductRequestDto);
    }

    @Test
    public void shouldReturnErrorWhenPriceIsZero() {
        ProductRequestDto product = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("0"))
                .withCategory(1L)
                .build();

        assertEquals(1, validator.validate(product).size());
    }

    @Test
    public void shouldToStringReturnAsExpected() {
        ProductRequestDto product = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .build();

        assertEquals("ProductRequestDto{Nome:'Galaxy S20', Quantidade:100, Descrição:'Celular top da " +
                "categoria', Preço:2000, CategoriaId:1}", product.toString());
    }
}
