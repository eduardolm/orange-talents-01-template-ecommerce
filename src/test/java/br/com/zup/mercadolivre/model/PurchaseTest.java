package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.controller.request.PaymentGatewayResponseDto;
import br.com.zup.mercadolivre.enums.PaymentGateway;
import br.com.zup.mercadolivre.enums.TransactionStatus;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.utils.builder.ProductBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Validator;
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
public class PurchaseTest {

    @MockBean
    private CategoryRepository categoryRepository;

    private User user;
    private Category category;
    private Product product;
    private Collection<CharacteristicsRequestDto> characteristics;

    @Autowired
    private Validator validator;

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
                .withCharacteristics(Lists.newArrayList(
                        new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();
        this.product.setId(1L);
    }

    @AfterEach
    public void rollbackDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    public void testToString() {
        assertEquals("Purchase{id=null, product=null, quantity=0, customer=null, paymentGateway=null, transactions=[]}",
                (new Purchase()).toString());
    }

    @Test
    public void shouldCreateNewPurchaseInstance() {
        Purchase purchase = new Purchase(this.product, 10, this.user, PaymentGateway.PAYPAL);

        assertTrue(purchase instanceof Purchase);
        assertEquals("Galaxy S20", purchase.getProduct().getName());
        assertEquals(new BigDecimal( "2000"), purchase.getProduct().getPrice());
    }

    @Test
    @DisplayName("Should add a new transaction")
    public void test1() {
        Purchase newPurchase = newPurchase();
        PaymentGatewayResponseDto paymentGatewayResponseDto = (purchase) ->
                new Transaction(TransactionStatus.sucesso, "1", purchase);
        newPurchase.addTransaction(paymentGatewayResponseDto);
    }

    @Test
    @DisplayName("Should not accept two equal transactions")
    public void test2() {
        Purchase newPurchase = newPurchase();
        PaymentGatewayResponseDto paymentGatewayResponseDto = (purchase) ->
                new Transaction(TransactionStatus.erro, "1", purchase);

        newPurchase.addTransaction(paymentGatewayResponseDto);

        PaymentGatewayResponseDto paymentGatewayResponseDto2 = (purchase) ->
                new Transaction(TransactionStatus.erro, "1", purchase);

        assertThrows(IllegalStateException.class, () ->  newPurchase.addTransaction(paymentGatewayResponseDto2));
    }

    @Test
    @DisplayName("Should not accept transaction if purchase already processed")
    public void test3() {
        Purchase newPurchase = newPurchase();
        PaymentGatewayResponseDto paymentGatewayResponseDto = (purchase) ->
                new Transaction(TransactionStatus.sucesso, "1", purchase);

        newPurchase.addTransaction(paymentGatewayResponseDto);

        PaymentGatewayResponseDto paymentGatewayResponseDto2 = (purchase) ->
                new Transaction(TransactionStatus.sucesso, "2", purchase);

        assertThrows(IllegalStateException.class, () -> newPurchase.addTransaction(paymentGatewayResponseDto2));
    }

    @DisplayName("Should verify if purchase completed successfully")
    @ParameterizedTest
    @MethodSource("generatorTest4")
    public void test4(boolean expectedResult, Collection<PaymentGatewayResponseDto> results) {
        Purchase newPurchase = newPurchase();
        results.forEach(newPurchase::addTransaction);

        assertEquals(expectedResult, newPurchase.successfullyProcessed());
    }

    private static Stream<Arguments> generatorTest4() {
        PaymentGatewayResponseDto successReturn1 = (purchase) ->
                new Transaction(TransactionStatus.sucesso, "1", purchase);

        PaymentGatewayResponseDto failureReturn1 = (purchase) ->
                new Transaction(TransactionStatus.erro, "1", purchase);

        return Stream.of(
                Arguments.of(true, List.of(successReturn1)),
                Arguments.of(false, List.of(failureReturn1)),
                Arguments.of(false, List.of())
        );
    }

    private Purchase newPurchase() {
        return new Purchase(this.product, 10, this.user, PaymentGateway.PAYPAL);
    }
}

