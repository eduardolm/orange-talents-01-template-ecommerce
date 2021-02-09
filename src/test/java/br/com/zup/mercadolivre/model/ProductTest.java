package br.com.zup.mercadolivre.model;


import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.controller.request.ProductRequestDto;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.utils.builder.ProductBuilder;
import br.com.zup.mercadolivre.utils.builder.ProductRequestDtoBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProductTest {

    @MockBean
    private CategoryRepository categoryRepository;

    private User user;
    private Category category;

    @BeforeEach
    public void setup() {
        this.user = new User("test@email.com", "pass1234");
        Category category = new Category("Celulares & Tablets");
        category.setId(1L);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        this.category = category;
    }

    @AfterEach
    public void rollbackDatabase() {
        categoryRepository.deleteAll();
    }

    @DisplayName("A product needs at least 3 characteristics")
    @ParameterizedTest
    @MethodSource("generatorTest1")
    public void test1(Collection<CharacteristicsRequestDto> characteristics) throws Exception {
        User owner = new User("email@email.com", "pass1234");
        new Product("Test Product", 10, "Description", BigDecimal.TEN, category, owner, characteristics);
    }

    static Stream<Arguments> generatorTest1() {
        return Stream.of(
                Arguments.of(List.of(
                        new CharacteristicsRequestDto("key", "value"),
                        new CharacteristicsRequestDto("key2", "value2"),
                        new CharacteristicsRequestDto("key3", "value3"))),
                Arguments.of(List.of(
                        new CharacteristicsRequestDto("key", "value"),
                        new CharacteristicsRequestDto("key2", "value2"),
                        new CharacteristicsRequestDto("key3", "value3"),
                        new CharacteristicsRequestDto("key4", "value4"))));
    }

    @DisplayName("A product cannot be created with less than 3 characteristics")
    @ParameterizedTest
    @MethodSource("generatorTest2")
    public void test2(Collection<CharacteristicsRequestDto> characteristics) throws Exception {
        User owner = new User("email@emil.com", "pass1234");

        assertThrows(IllegalArgumentException.class, () ->
        new Product("Test Product", 10, "Description", BigDecimal.TEN, category, owner, characteristics));
    }

    static Stream<Arguments> generatorTest2() {
        return Stream.of(
                Arguments.of(List.of(
                        new CharacteristicsRequestDto("key2", "value2"),
                        new CharacteristicsRequestDto("key3", "value3"))),
                Arguments.of(List.of(
                        new CharacteristicsRequestDto("key4", "value4"))));
    }

    @Test
    public void shouldCreateANewProductInstance() {
        Product product = new ProductBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(new Category("Celulares & Tablets"))
                .withCharacteristics(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        assertTrue(product instanceof Product);
    }

    @Test
    public void shouldTwoProductInstancesNotBeEqualExceptIfEmpty() {

        Product product = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build().toModel(categoryRepository, user);

        Product product2 = new ProductRequestDtoBuilder()
                .withName("Galaxy S15")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build().toModel(categoryRepository, user);

        User product3 = new User();
        User product4 = new User();

        assertNotEquals(product.hashCode(), product2.hashCode());
        assertEquals(product3.hashCode(), product4.hashCode());
        assertNotEquals(product3.hashCode(), product.hashCode());
    }

    @Test
    public void shouldTwoIdenticalInstancesBeEqual() {
        Product product = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build().toModel(categoryRepository, user);

        Product product2 = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build().toModel(categoryRepository, user);

        assertEquals(product.hashCode(), product2.hashCode());
    }

    @Test
    public void shouldGettersWorkCorrectly() {
        Product product = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build().toModel(categoryRepository, user);

        assertEquals("Galaxy S20", product.getName());
        assertEquals(100, product.getQuantity());
        assertEquals("Celular top da categoria", product.getDescription());
        assertEquals(new BigDecimal("2000"), product.getPrice());
        assertEquals(new Category("Celulares & Tablets").toString(), product.getCategory().toString());
    }
}
