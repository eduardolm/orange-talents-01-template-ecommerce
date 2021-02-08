package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.controller.request.PurchaseRequestDto;
import br.com.zup.mercadolivre.enums.PaymentGateway;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.UserRepository;
import br.com.zup.mercadolivre.service.EmailService;
import br.com.zup.mercadolivre.utils.builder.ProductBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PurchaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;;

    @Autowired
    private PurchaseController controller;

    @Autowired
    private EmailService email;

    private UriComponentsBuilder uriComponentsBuilder = Mockito.mock(UriComponentsBuilder.class);

    private User user;
    private Category category;
    private Product product;
    private List<CharacteristicsRequestDto> characteristics;
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

    @Test
    @DisplayName("Throws exception when stock is not available")
    public void test2() throws BindException {

        PurchaseRequestDto request = new PurchaseRequestDto(2, this.product.getId(), PaymentGateway.PAG_SEGURO);

        assertThrows(BindException.class, () -> controller.create(request, uriComponentsBuilder));
    }
}
