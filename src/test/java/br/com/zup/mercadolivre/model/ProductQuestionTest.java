package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.utils.builder.ProductRequestDtoBuilder;
import br.com.zup.mercadolivre.utils.builder.QuestionRequestDtoBuilder;
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
public class ProductQuestionTest {

    @MockBean
    private CategoryRepository categoryRepository;

    private User user;
    private Category category;
    private Product product;
    private Collection<CharacteristicsRequestDto> characteristics;

    @BeforeEach
    public void setup() {
        // Create User
        this.user = new User("test@email.com", "pass1234");

        // Create Category
        this.category = new Category("Celulares & Tablets");
        this.category.setId(1L);
        when(categoryRepository.findById(this.category.getId())).thenReturn(Optional.of(this.category));


        // Create Characteristics
        this.characteristics = Lists.newArrayList(
                new CharacteristicsRequestDto("key", "value"),
                new CharacteristicsRequestDto("key2", "value2"),
                new CharacteristicsRequestDto("key3", "value3")
        );

        // Create Product
        this.product = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(1L)
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build().toModel(categoryRepository, this.user);
    }

    @Test
    public void testConstructor() {
        User interested = new User();

        ProductQuestion actualProductQuestion = new ProductQuestion("Dr", interested, new Product());

        assertNull(actualProductQuestion.getProductOwner());
        assertEquals("Question{Id:null, Título:'Dr', Interessado:null, Produto:null}", actualProductQuestion.toString());
        assertEquals("Dr", actualProductQuestion.getTitle());
    }

    @Test
    public void testToString() {
        User interested = new User();

        assertEquals("Question{Id:null, Título:'Dr', Interessado:null, Produto:null}",
                (new ProductQuestion("Dr", interested, new Product())).toString());
    }

    @Test
    public void testToString2() {
        Product product = new Product();
        product.setId(123L);

        assertEquals("Question{Id:null, Título:'Dr', Interessado:null, Produto:null}",
                (new ProductQuestion("Dr", new User(), product)).toString());
    }

    @Test
    public void testGetProductOwner() {
        User interested = new User();

        assertNull((new ProductQuestion("Dr", interested, new Product())).getProductOwner());
    }

    @Test
    public void testEquals() {
        assertNotEquals((new ProductQuestion()), "o");
    }

    @Test
    public void testEquals2() {
        ProductQuestion productQuestion = new ProductQuestion();

        assertEquals(new ProductQuestion(), productQuestion);
    }

    @Test
    public void testEquals3() {
        ProductQuestion productQuestion = new ProductQuestion();
        User interested = new User();

        assertNotEquals(new ProductQuestion("Dr", interested, new Product()), productQuestion);
    }

    @Test
    public void testEquals4() {
        User interested = new User();
        ProductQuestion productQuestion = new ProductQuestion("Dr", interested, new Product());

        assertNotEquals(new ProductQuestion(), productQuestion);
    }

    @Test
    public void testEquals5() {
        ProductQuestion productQuestion = new ProductQuestion("Dr", null, new Product());
        User interested = new User();

        assertNotEquals(new ProductQuestion("Dr", interested, new Product()), productQuestion);
    }

    @Test
    public void testEquals6() {
        User interested = new User("jane.doe@example.org", "iloveyou");
        ProductQuestion productQuestion = new ProductQuestion("Dr", interested, new Product());
        User interested1 = new User();

        assertNotEquals(new ProductQuestion("Dr", interested1, new Product()), productQuestion);
    }

    @Test
    public void testHashCode() {
        assertEquals(0, (new ProductQuestion()).hashCode());
        assertEquals(2165133, (new ProductQuestion("Dr", new User(), null)).hashCode());
    }

    @Test
    public void testCompareTo() {
        User interested = new User();
        ProductQuestion productQuestion = new ProductQuestion("Dr", interested, new Product());
        User interested1 = new User();

        assertEquals(0, productQuestion.compareTo(new ProductQuestion("Dr", interested1, new Product())));
    }

    @AfterEach
    public void rollbackDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    public void shouldQuestionRequestDtoCreateAQuestionInstance() {
        ProductQuestion productQuestion = new QuestionRequestDtoBuilder()
                .withTitle("Test title")
                .build().toModel(this.user, this.product);

        assertNotNull(productQuestion);
    }

    @Test
    public void shouldGettersReturnAsExpectedQuestionProperties() {
        ProductQuestion productQuestion = new QuestionRequestDtoBuilder()
                .withTitle("Test title")
                .build().toModel(this.user, this.product);

        assertEquals("Test title", productQuestion.getTitle());
        assertEquals(user.getEmail(), productQuestion.getInterested().getEmail());
        assertEquals(product.getName(), productQuestion.getProduct().getName());
    }
}
