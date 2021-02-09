package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.enums.PaymentGateway;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.utils.builder.ProductBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PurchaseRequestDtoTest {

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
                .withCharacteristics(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
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
    public void shouldCreateNewPurchaseRequestDtoInstance() {
        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto(10, this.product.getId(), PaymentGateway.PAG_SEGURO);

        assertTrue(purchaseRequestDto instanceof PurchaseRequestDto);
    }

    @Test
    public void shouldReturnErrorIfQuantityLesserOrEqualsZero() {
        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto( -20, this.product.getId(), PaymentGateway.PAG_SEGURO);
        PurchaseRequestDto purchaseRequestDto2 = new PurchaseRequestDto( 0, this.product.getId(), PaymentGateway.PAG_SEGURO);

        assertEquals(1, validator.validate(purchaseRequestDto).size());
        assertEquals(1, validator.validate(purchaseRequestDto2).size());
    }

    @Test
    public void shouldReturnErrorWhenProductIdNotSet() {
        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto( 20, null, PaymentGateway.PAG_SEGURO);

        assertEquals(1, validator.validate(purchaseRequestDto).size());
    }

    @Test
    public void shouldReturnErrorWhenPaymentGatewayNotSet() {
        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto( 20, this.product.getId(), null);

        assertEquals(1, validator.validate(purchaseRequestDto).size());
    }
}
