package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductQuestion;
import br.com.zup.mercadolivre.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductQuestionDtoTest {
    @Test
    public void testConstructor() {
        User interested = new User();

        ProductQuestionDto actualProductQuestionDto = new ProductQuestionDto(
                new ProductQuestion("Dr", interested, new Product()));

        assertNull(actualProductQuestionDto.getId());
        assertNull(actualProductQuestionDto.getProduct());
        assertEquals("Dr", actualProductQuestionDto.getTitle());
        assertNull(actualProductQuestionDto.getInterested());
    }

    @Test
    public void testConstructor2() {
        User interested = new User();

        ProductQuestionDto actualProductQuestionDto = new ProductQuestionDto(
                new ProductQuestion("Title", interested, new Product()));

        assertNull(actualProductQuestionDto.getId());
        assertNull(actualProductQuestionDto.getProduct());
        assertEquals("Title", actualProductQuestionDto.getTitle());
        assertNull(actualProductQuestionDto.getInterested());
    }
}

