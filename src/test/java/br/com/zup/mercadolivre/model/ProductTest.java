package br.com.zup.mercadolivre.model;


import br.com.zup.mercadolivre.utils.builder.ProductBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void shouldCreateANewProductInstance() {
        Product product = new ProductBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(new Category("Celulares & Tablets"))
                .build();

        assertTrue(product instanceof Product);
    }

    @Test
    public void shouldTwoProductInstancesNotBeEqualExceptIfEmpty() {

        Product product = new ProductBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(new Category("Celulares & Tablets"))
                .build();

        Product product2 = new ProductBuilder()
                .withName("Galaxy S15")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(new Category("Celulares & Tablets"))
                .build();

        User product3 = new User();
        User product4 = new User();

        assertNotEquals(product.hashCode(), product2.hashCode());
        assertEquals(product3.hashCode(), product4.hashCode());
        assertNotEquals(product3.hashCode(), product.hashCode());
    }

    @Test
    public void shouldTwoIdenticalInstancesBeEqual() {
        Product product = new ProductBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(new Category("Celulares & Tablets"))
                .build();

        Product product2 = new ProductBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(new Category("Celulares & Tablets"))
                .build();

        assertEquals(product.hashCode(), product2.hashCode());
    }

    @Test
    public void shouldGettersWorkCorrectly() {
        Product product = new ProductBuilder()
                .withName("Galaxy S20")
                .withQuantity(100)
                .withDescription("Celular top da categoria")
                .withPrice(new BigDecimal("2000"))
                .withCategory(new Category("Celulares & Tablets"))
                .build();

        assertEquals("Galaxy S20", product.getName());
        assertEquals(100, product.getQuantity());
        assertEquals("Celular top da categoria", product.getDescription());
        assertEquals(new BigDecimal("2000"), product.getPrice());
        assertEquals(new Category("Celulares & Tablets"), product.getCategory());
    }
}
