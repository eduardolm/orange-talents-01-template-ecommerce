package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.utils.builder.ProductRequestDtoBuilder;
import br.com.zup.mercadolivre.utils.builder.UserBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ProductRequestDtoTest {

    @Autowired
    private Validator validator;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setup() {
        // Create Category
        var existingCategory = categoryRepository.findById(1L);
        if (existingCategory.isEmpty()) {
            Category category = new Category("Celulares & Tablets");
            categoryRepository.save(category);
            this.category = category;
        }
        else {
            this.category = existingCategory.get();
        }
    }

    @Test
    public void testConstructor() {
        assertEquals("ProductRequestDto{Preço:'null', Quantidade:null, Descrição:'null', Preço:null, CategoriaId:null,"
                + " Características:[]}", (new ProductRequestDto()).toString());
    }

    @Test
    public void testConstructor2() {
        BigDecimal price = BigDecimal.valueOf(42L);

        ProductRequestDto actualProductRequestDto = new ProductRequestDto("Name", 1,
                "The characteristics of someone or something", price, 123L, new ArrayList<>());

        assertEquals("Name", actualProductRequestDto.getName());
        assertEquals(1, actualProductRequestDto.getQuantity().intValue());
        assertEquals(
                "ProductRequestDto{Preço:'Name', Quantidade:1, Descrição:'The characteristics of someone or something',"
                        + " Preço:42, CategoriaId:123, Características:[]}",
                actualProductRequestDto.toString());
        assertEquals("The characteristics of someone or something", actualProductRequestDto.getDescription());
        assertEquals(123L, actualProductRequestDto.getCategoryId().longValue());
        assertTrue(actualProductRequestDto.getCharacteristics().isEmpty());
        assertEquals("42", actualProductRequestDto.getPrice().toString());
    }

    @ParameterizedTest
    @MethodSource("generator")
    @DisplayName("Create product with several characteristics")
    public void test1(int expected, List<CharacteristicsRequestDto> newCharacteristics) {
        ProductRequestDto productRequestDto = new ProductRequestDtoBuilder()
                .withName("Test product")
                .withQuantity(16)
                .withDescription("Testing product")
                .withPrice(new BigDecimal("500"))
                .withCategory(this.category.getId())
                .withCharacteristcs(newCharacteristics)
                .build();

        assertEquals(expected, productRequestDto.findRepeatedCharacteristics().size());
    }

    private static Stream<Arguments> generator() {
        return Stream.of(
                Arguments.of(0, List.of()),
                Arguments.of(0, List.of(new CharacteristicsRequestDto("key", "value"))),
                Arguments.of(0, List.of(
                        new CharacteristicsRequestDto("key", "value"),
                        new CharacteristicsRequestDto("key1", "value1")))
        );
    }

    @Test
    public void shouldCreateNewInstanceOfProductRequestDto() {
        ProductRequestDto productRequestDto = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(this.category.getId())
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        assertNotNull(productRequestDto);
    }

    @Test
    public void shouldToModelCreateProductInstance() {
        User user = new UserBuilder().withEmail("user2@email.com").withPassword("pass1234").build();

        Product product = (new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(this.category.getId())
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build()).toModel(categoryRepository, user);

        assertNotNull(product);
        assertEquals("Galaxy S20", product.getName());
    }

    @Test
    public void testFindRepeatedCharacteristics() {
        assertTrue((new ProductRequestDto()).findRepeatedCharacteristics().isEmpty());
    }

    @Test
    public void testFindRepeatedCharacteristics2() {
        ArrayList<CharacteristicsRequestDto> characteristicsRequestDtoList = new ArrayList<>();
        characteristicsRequestDtoList
                .add(new CharacteristicsRequestDto("Name", "The characteristics of someone or something"));

        assertTrue((new ProductRequestDto("Name", 1, "The characteristics of someone or something", BigDecimal.valueOf(42L),
                123L, characteristicsRequestDtoList)).findRepeatedCharacteristics().isEmpty());
    }

    @Test
    public void testFindRepeatedCharacteristics3() {
        ArrayList<CharacteristicsRequestDto> characteristicsRequestDtoList = new ArrayList<>();
        characteristicsRequestDtoList
                .add(new CharacteristicsRequestDto("Name", "The characteristics of someone or something"));
        characteristicsRequestDtoList
                .add(new CharacteristicsRequestDto("Name", "The characteristics of someone or something"));

        Set<String> actualFindRepeatedCharacteristicsResult = (new ProductRequestDto("Name", 1,
                "The characteristics of someone or something", BigDecimal.valueOf(42L), 123L, characteristicsRequestDtoList))
                .findRepeatedCharacteristics();

        assertEquals(1, actualFindRepeatedCharacteristicsResult.size());
        assertTrue(actualFindRepeatedCharacteristicsResult.contains("Name"));
    }

    @Test
    public void shouldReturnErrorWhenNameIsBlank() {
        ProductRequestDto product = new ProductRequestDtoBuilder()
                .withName("")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(this.category.getId())
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        var edu = validator.validate(product);
        assertEquals(1, validator.validate(product).size());
    }

    @Test
    public void shouldBeOkWhenQuantityIsNull() {
        ProductRequestDto product = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(0)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(this.category.getId())
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        assertNotNull(product);
    }

    @Test
    public void shouldReturnErrorWhenPriceIsZero() {
        ProductRequestDto product = new ProductRequestDtoBuilder()
                .withName("Galaxy S200")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("0"))
                .withCategory(this.category.getId())
                .withCharacteristcs(Lists.newArrayList(
                        new CharacteristicsRequestDto("Peso", "145g"),
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
                .withCategory(this.category.getId())
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        assertEquals("ProductRequestDto{Preço:'Galaxy S20', Quantidade:100, Descrição:'Celular top da " +
                "categoria', Preço:2000, CategoriaId:1, Características:[Peso: 145g, Conectividade: 5G, Wi-Fi, " +
                "Bluetooth, Itens incluídos: Celular, carregador, cabo mini usb]}", product.toString());
    }

    @Test
    public void testToString() {
        assertEquals("ProductRequestDto{Preço:'null', Quantidade:null, Descrição:'null', Preço:null, CategoriaId:null,"
                + " Características:[]}", (new ProductRequestDto()).toString());
    }

    @Test
    public void testToString2() {
        ArrayList<CharacteristicsRequestDto> characteristicsRequestDtoList = new ArrayList<>();
        characteristicsRequestDtoList
                .add(new CharacteristicsRequestDto("Name", "The characteristics of someone or something"));

        assertEquals(
                "ProductRequestDto{Preço:'Name', Quantidade:1, Descrição:'The characteristics of someone or something',"
                        + " Preço:42, CategoriaId:123, Características:[Name: The characteristics of someone or something]}",
                (new ProductRequestDto("Name", 1, "The characteristics of someone or something", BigDecimal.valueOf(42L), 123L,
                        characteristicsRequestDtoList)).toString());
    }

    @Test
    public void testToString3() {
        ArrayList<CharacteristicsRequestDto> characteristicsRequestDtoList = new ArrayList<>();
        characteristicsRequestDtoList
                .add(new CharacteristicsRequestDto("Name", "The characteristics of someone or something"));
        characteristicsRequestDtoList
                .add(new CharacteristicsRequestDto("Name", "The characteristics of someone or something"));

        assertEquals(
                "ProductRequestDto{Preço:'Name', Quantidade:1, Descrição:'The characteristics of someone or something',"
                        + " Preço:42, CategoriaId:123, Características:[Name: The characteristics of someone or something, Name:"
                        + " The characteristics of someone or something]}",
                (new ProductRequestDto("Name", 1, "The characteristics of someone or something", BigDecimal.valueOf(42L), 123L,
                        characteristicsRequestDtoList)).toString());
    }
}
