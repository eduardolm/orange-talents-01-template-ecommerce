package br.com.zup.mercadolivre.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProductCharacteristicsTest {
    @Test
    public void testToString() {
        assertEquals("ProductCharacteristics{Id:null, Nome:'null', Descrição:'null'}",
                (new ProductCharacteristics()).toString());
        assertEquals("ProductCharacteristics{Id:null, Nome:'null', Descrição:'null'}",
                (new ProductCharacteristics()).toString());
    }

    @Test
    public void testEquals() {
        assertNotEquals((new ProductCharacteristics()), "o");
        assertNotEquals((new ProductCharacteristics()), 0);
        assertNotEquals((new ProductCharacteristics()), "o");
        assertNotEquals((new ProductCharacteristics()), 0);
    }

    @Test
    public void testEquals2() {
        ProductCharacteristics productCharacteristics = new ProductCharacteristics("Name",
                "The characteristics of someone or something", new Product());

        assertEquals(new ProductCharacteristics("Name", "The characteristics of someone or something", new Product()), productCharacteristics);
    }

    @Test
    public void testEquals5() {
        ProductCharacteristics productCharacteristics = new ProductCharacteristics("Name",
                "The characteristics of someone or something", new Product());

        assertEquals(new ProductCharacteristics("Name", "The characteristics of someone or something", new Product()), productCharacteristics);
    }

    @Test
    public void testHashCode() {
        assertEquals(1334610322,
                (new ProductCharacteristics("Name", "The characteristics of someone or something", new Product())).hashCode());
        assertEquals(1334610322,
                (new ProductCharacteristics("Name", "The characteristics of someone or something", new Product())).hashCode());
    }
}

