package br.com.zup.mercadolivre.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductReview;
import br.com.zup.mercadolivre.model.User;
import org.junit.jupiter.api.Test;

public class ProductReviewDtoTest {
    @Test
    public void shouldEmptyConstructorCreateNewProductReviewDtoInstance() {
        ProductReviewDto actualProductReviewDto = new ProductReviewDto(new ProductReview());

        assertNull(actualProductReviewDto.getGrade());
        assertNull(actualProductReviewDto.getTitle());
        assertNull(actualProductReviewDto.getDescription());
    }

    @Test
    public void shouldGettersWorkAsExpectedInProductReviewDto() {
        Product product = new Product();

        ProductReviewDto actualProductReviewDto = new ProductReviewDto(
                new ProductReview(0, "Dr", "The characteristics of someone or something", product, new User()));

        assertEquals(0, actualProductReviewDto.getGrade().intValue());
        assertEquals("Dr", actualProductReviewDto.getTitle());
        assertEquals("The characteristics of someone or something", actualProductReviewDto.getDescription());
    }
}

