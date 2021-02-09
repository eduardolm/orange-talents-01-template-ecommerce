package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductQuestion;
import br.com.zup.mercadolivre.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductQuestionRequestDtoTest {
    @Test
    public void testSetTitle() {
        // Arrange
        ProductQuestionRequestDto productQuestionRequestDto = new ProductQuestionRequestDto();

        // Act
        productQuestionRequestDto.setTitle("Dr");

        // Assert
        assertEquals("QuestionRequestDto{Título:'Dr'}", productQuestionRequestDto.toString());
    }

    @Test
    public void testToString() {
        // Arrange, Act and Assert
        assertEquals("QuestionRequestDto{Título:'null'}", (new ProductQuestionRequestDto()).toString());
    }

    @Test
    public void testToModel() {
        // Arrange
        ProductQuestionRequestDto productQuestionRequestDto = new ProductQuestionRequestDto();
        User interested = new User();

        // Act
        ProductQuestion actualToModelResult = productQuestionRequestDto.toModel(interested, new Product());

        // Assert
        assertNull(actualToModelResult.getProductOwner());
        assertEquals("Question{Id:null, Título:'null', Interessado:null, Produto:null}", actualToModelResult.toString());
        assertNull(actualToModelResult.getTitle());
        assertTrue(actualToModelResult instanceof ProductQuestion);
    }
}

