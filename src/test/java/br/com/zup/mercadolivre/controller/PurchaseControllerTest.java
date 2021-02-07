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
import br.com.zup.mercadolivre.service.EmailMessages;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PurchaseControllerTest {

    private EntityManager manager = Mockito.mock(EntityManager.class);
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private EmailMessages email = Mockito.mock(EmailMessages.class);
//    private PurchaseController controller = new PurchaseController(manager, userRepository, email);
    private User owner = new User("emaildedono@mail.com", "pass1234");
    private UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
            .fromHttpUrl("http://localhost:8080");

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private PurchaseRepository purchaseRepository;

    @MockBean
    private PurchaseController controller;

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

        Mockito.when(userRepository.findUserByEmail("emaildedono@mail.com")).thenReturn(Optional.of(owner));
    }

    @AfterEach
    public void rollbackDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Redirects to payment gateway when stock accepted")
    public void test1() throws Exception {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(this.product));

        Mockito.doAnswer(invocation -> {
            Purchase purchase = invocation.<Purchase>getArgument(0);
            ReflectionTestUtils.setField(purchase, "id", 1L);
            return null;
        }).when(purchaseRepository.save(Mockito.any(Purchase.class)));

        PurchaseRequestDto request = new PurchaseRequestDto(1, 1L, PaymentGateway.PAG_SEGURO);

        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).thenReturn(new Purchase(this.product, request.getQuantity(), this.user, PaymentGateway.PAYPAL));
        String address = Objects.requireNonNull(controller.create(request, uriComponentsBuilder).getBody()).toString();

        assertEquals("pagseguro.com/1?redirectUrl=http://localhost:8080/retorno-pagseguro", address);

        ArgumentCaptor<Purchase> purchaseArgument = ArgumentCaptor.forClass(Purchase.class);

        //        Mockito.verify(email).purchase(purchaseArgument.capture()); // Testar envio de e-mail

        // TODO: Fazer esse teste passar
    }
}
