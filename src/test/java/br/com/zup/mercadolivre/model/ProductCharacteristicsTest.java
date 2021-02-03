package br.com.zup.mercadolivre.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ProductCharacteristicsTest {
    @Test
    public void testToString() {
        // Arrange, Act and Assert
        assertEquals("ProductCharacteristics{Id:null, Nome:'null', Descrição:'null', Produto:null}",
                (new ProductCharacteristics()).toString());
    }

    @Test
    public void testEquals() {
        // Arrange, Act and Assert
        assertFalse((new ProductCharacteristics()).equals("o"));
        assertFalse((new ProductCharacteristics()).equals(0));
    }

    @Test
    public void testEquals2() {
        // Arrange
        ProductCharacteristics productCharacteristics = new ProductCharacteristics("Name",
                "The characteristics of someone or something", new Product());

        // Act and Assert
        assertFalse(productCharacteristics.equals(new ProductCharacteristics()));
    }

    @Test
    public void testEquals3() {
        // Arrange
        ProductCharacteristics productCharacteristics = new ProductCharacteristics("Name",
                "The characteristics of someone or something", new Product());

        // Act and Assert
        assertTrue(productCharacteristics
                .equals(new ProductCharacteristics("Name", "The characteristics of someone or something", new Product())));
    }

    @Test
    public void testHashCode() {
        // Arrange, Act and Assert
        assertEquals(1334610322,
                (new ProductCharacteristics("Name", "The characteristics of someone or something", new Product())).hashCode());
    }
}

