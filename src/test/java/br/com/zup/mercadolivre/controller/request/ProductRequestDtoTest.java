package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.utils.builder.CategoryBuilder;
import br.com.zup.mercadolivre.utils.builder.ProductRequestDtoBuilder;
import br.com.zup.mercadolivre.utils.builder.UserBuilder;
import org.assertj.core.util.Lists;
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
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        assertTrue(productRequestDto instanceof ProductRequestDto);
    }

    @Test
    public void shouldToModelCreateProductInstance() {
        User user = new UserBuilder().withEmail("user2@email.com").withPassword("pass1234").build();

        Product product = (new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build()).toModel(categoryRepository, user);

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
                .withCategory(2L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
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
                .withCategory(2L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
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
                .withCategory(2L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
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
                .withCategory(2L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        assertEquals("ProductRequestDto{Preço:'Galaxy S20', Quantidade:100, Descrição:'Celular top da " +
                "categoria', Preço:2000, CategoriaId:2, Características:[Peso: 145g, Conectividade: 5G, Wi-Fi, " +
                "Bluetooth, Itens incluídos: Celular, carregador, cabo mini usb]}", product.toString());
    }
}
