package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductCharacteristics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCharacteristicsDtoTest {
    @Test
    public void shouldConstructorCreateANewProductCharacteristicsInstance() {
        ProductCharacteristicsDto actualProductCharacteristicsDto = new ProductCharacteristicsDto(
                new ProductCharacteristics());

        assertTrue(actualProductCharacteristicsDto instanceof ProductCharacteristicsDto);
        assertNull(actualProductCharacteristicsDto.getName());
        assertNull(actualProductCharacteristicsDto.getId());
        assertNull(actualProductCharacteristicsDto.getDescription());
    }

    @Test
    public void shouldOverloadedConstructorCreateANewProductCharacteristicsInstance() {
        ProductCharacteristicsDto actualProductCharacteristicsDto = new ProductCharacteristicsDto(
                new ProductCharacteristics("br.com.zup.mercadolivre.model.ProductCharacteristics",
                        "The characteristics of someone or something", new Product()));

        assertEquals("br.com.zup.mercadolivre.model.ProductCharacteristics", actualProductCharacteristicsDto.getName());
        assertNull(actualProductCharacteristicsDto.getId());
        assertEquals("The characteristics of someone or something", actualProductCharacteristicsDto.getDescription());
    }

    @Test
    public void testConstructor() {
        ProductCharacteristicsDto actualProductCharacteristicsDto = new ProductCharacteristicsDto(
                new ProductCharacteristics());

        assertNull(actualProductCharacteristicsDto.getName());
        assertNull(actualProductCharacteristicsDto.getId());
        assertNull(actualProductCharacteristicsDto.getDescription());
    }

    @Test
    public void testConstructor2() {
        ProductCharacteristicsDto actualProductCharacteristicsDto = new ProductCharacteristicsDto(
                new ProductCharacteristics("br.com.zup.mercadolivre.model.ProductCharacteristics",
                        "The characteristics of someone or something", new Product()));

        assertEquals("br.com.zup.mercadolivre.model.ProductCharacteristics", actualProductCharacteristicsDto.getName());
        assertNull(actualProductCharacteristicsDto.getId());
        assertEquals("The characteristics of someone or something", actualProductCharacteristicsDto.getDescription());
    }
}

