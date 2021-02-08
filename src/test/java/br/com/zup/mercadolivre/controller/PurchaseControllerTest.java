package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.controller.request.PurchaseRequestDto;
import br.com.zup.mercadolivre.enums.PaymentGateway;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.PurchaseRepository;
import br.com.zup.mercadolivre.repository.UserRepository;
import br.com.zup.mercadolivre.service.EmailService;
import br.com.zup.mercadolivre.service.IEmailService;
import br.com.zup.mercadolivre.utils.builder.ProductBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseController controller;

    @Autowired
    private EmailService email;

    private UriComponentsBuilder uriComponentsBuilder = Mockito.mock(UriComponentsBuilder.class);

    private User user;
    private Category category;
    private Product product;
    private List<CharacteristicsRequestDto> characteristics;
    private Purchase purchase;
    private User owner;

    @BeforeEach
    public void setup() {
        // Create User
        var existingUser = userRepository.findUserByEmail("test@email.com");
        if (existingUser.isEmpty()) {
            User user = new User("test@email.com", "pass1234");
            userRepository.save(user);
            this.user = user;
        }
        else {
            this.user = existingUser.get();
        }

        var existingOwner = userRepository.findUserByEmail("owner@mail.com");
        if (existingOwner.isEmpty()) {
            User owner = new User("owner@mail.com", "pass1234");
            userRepository.save(owner);
            this.owner = owner;
        }
        else {
            this.owner = existingOwner.get();
        }


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

        // Create Characteristics
        this.characteristics = Lists.newArrayList(
                new CharacteristicsRequestDto("key", "value"),
                new CharacteristicsRequestDto("key2", "value2"),
                new CharacteristicsRequestDto("key3", "value3")
        );

        // Create product
        var existingProduct = productRepository.findById(1L);
        if (existingProduct.isEmpty()) {
            Product product = new ProductBuilder()
                    .withName("Galaxy S20")
                    .withQuantity(1)
                    .withDescription("Celular top da categoria")
                    .withPrice(new BigDecimal("2000"))
                    .withCategory(this.category)
                    .withCharacteristics(this.characteristics)
                    .withProductOwner(this.user)
                    .build();

            productRepository.save(product);
            this.product = product;
        }
        else {
            this.product = existingProduct.get();
        }

    }

    @AfterEach
    public void rollbackDatabase() {
//        categoryRepository.deleteAll();
//        productRepository.deleteAll();
//        purchaseRepository.deleteAll();
//        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Redirects to payment gateway when stock accepted")
    public void test1() throws Exception {
//        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(this.product));
//
//        Mockito.doAnswer(invocation -> {
//            this.purchase = invocation.getArgument(0);
//            ReflectionTestUtils.setField(purchase, "id", 1L);
//            return null;
//        }).when(purchaseRepository).save(Mockito.any(Purchase.class));
//
        PurchaseRequestDto request = new PurchaseRequestDto(1, 1L, PaymentGateway.PAG_SEGURO);
//
//        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class)))
//                .thenReturn(new Purchase(this.product, request.getQuantity(), this.user, PaymentGateway.PAG_SEGURO));
//
        String address = Objects.requireNonNull(controller.create(request, uriComponentsBuilder).getBody()).toString();

        assertEquals("pagseguro.com/1?redirectUrl=http://localhost:8080/retorno-pagseguro", address);

        ArgumentCaptor<Purchase> purchaseArgument = ArgumentCaptor.forClass(Purchase.class);

        //Mockito.verify(email).purchase(purchaseArgument.capture());  Testar envio de e-mail

        // TODO: Fazer esse teste passar
    }

    @Test
    @DisplayName("Throws exception when stock is not available")
    public void test2() throws BindException {

        PurchaseRequestDto request = new PurchaseRequestDto(2, this.product.getId(), PaymentGateway.PAG_SEGURO);

        assertThrows(BindException.class, () -> controller.create(request, uriComponentsBuilder));
    }
}
