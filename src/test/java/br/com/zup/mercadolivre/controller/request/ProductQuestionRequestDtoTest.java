package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductQuestion;
import br.com.zup.mercadolivre.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductQuestionRequestDtoTest {
    @Test
    public void testSetTitle() {
        ProductQuestionRequestDto productQuestionRequestDto = new ProductQuestionRequestDto();

        productQuestionRequestDto.setTitle("Dr");

        assertEquals("QuestionRequestDto{Título:'Dr'}", productQuestionRequestDto.toString());
    }

    @Test
    public void testSetTitle2() {
        ProductQuestionRequestDto productQuestionRequestDto = new ProductQuestionRequestDto();

        productQuestionRequestDto.setTitle("Dr");

        assertEquals("QuestionRequestDto{Título:'Dr'}", productQuestionRequestDto.toString());
    }

    @Test
    public void testToString() {
        assertEquals("QuestionRequestDto{Título:'null'}", (new ProductQuestionRequestDto()).toString());
        assertEquals("QuestionRequestDto{Título:'null'}", (new ProductQuestionRequestDto()).toString());
    }

    @Test
    public void testToModel() {
        ProductQuestionRequestDto productQuestionRequestDto = new ProductQuestionRequestDto();
        User interested = new User();

        ProductQuestion actualToModelResult = productQuestionRequestDto.toModel(interested, new Product());

        assertNull(actualToModelResult.getProductOwner());
        assertEquals("Question{Id:null, Título:'null', Interessado:null, Produto:null}", actualToModelResult.toString());
        assertNull(actualToModelResult.getTitle());
        assertTrue(actualToModelResult instanceof ProductQuestion);
    }

    @Test
    public void testToModel2() {
        ProductQuestionRequestDto productQuestionRequestDto = new ProductQuestionRequestDto();
        User interested = new User();

        ProductQuestion actualToModelResult = productQuestionRequestDto.toModel(interested, new Product());

        assertNull(actualToModelResult.getProductOwner());
        assertEquals("Question{Id:null, Título:'null', Interessado:null, Produto:null}", actualToModelResult.toString());
        assertNull(actualToModelResult.getTitle());
    }
}

