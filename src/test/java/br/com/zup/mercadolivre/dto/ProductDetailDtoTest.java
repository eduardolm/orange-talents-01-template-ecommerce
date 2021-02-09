package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.controller.request.ProductRequestDto;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.utils.builder.ProductRequestDtoBuilder;
import br.com.zup.mercadolivre.utils.builder.UserBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProductDetailDtoTest {

    @MockBean
    private ProductRepository repository;

    @MockBean
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setup() {
        Category category = new Category("Celulares & Tablets");
        category.setId(1L);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        this.category = category;
    }

    @AfterEach
    public void rollbackDatabase() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateProductDetailDtoInstance() {
        User user = new UserBuilder().withEmail("user@email.com").withPassword("pass1234").build();
        ProductRequestDto productRequestDto = new ProductRequestDtoBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(category.getId())
                .withCharacteristcs(Lists.newArrayList(new CharacteristicsRequestDto("Peso", "145g"),
                        new CharacteristicsRequestDto("Conectividade", "5G, Wi-Fi, Bluetooth"),
                        new CharacteristicsRequestDto("Itens incluídos", "Celular, carregador, cabo mini usb")))
                .build();

        Product product = productRequestDto.toModel(categoryRepository, user);
        ProductDetailDto productDetailDto = new ProductDetailDto(product);

        assertTrue(productDetailDto instanceof ProductDetailDto);
        assertEquals(3, productDetailDto.getCharacteristics().size());
        assertEquals("Galaxy S20", productDetailDto.getName());
        assertEquals(100, productDetailDto.getQuantity());
        assertEquals("Celular top da categoria", productDetailDto.getDescription());
        assertEquals(new BigDecimal("2000"), productDetailDto.getPrice());
        assertEquals("Celulares & Tablets", productDetailDto.getCategory().getName());
        assertEquals("user@email.com", productDetailDto.getProductOwner().getEmail());
    }
}
