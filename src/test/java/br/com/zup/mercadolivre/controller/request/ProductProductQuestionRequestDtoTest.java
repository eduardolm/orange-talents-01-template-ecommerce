package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.utils.builder.QuestionRequestDtoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProductProductQuestionRequestDtoTest {

    @Test
    public void shouldCreateQuestionRequestDtoInstance() {
        ProductQuestionRequestDto productQuestionRequestDto = new QuestionRequestDtoBuilder()
                .withTitle("Test title")
                .build();

        assertTrue(productQuestionRequestDto instanceof ProductQuestionRequestDto);
    }

    @Test
    public void shouldGettersReturnPropertiesAsRequested() {
        ProductQuestionRequestDto productQuestionRequestDto = new QuestionRequestDtoBuilder()
                .withTitle("Test title")
                .build();

        assertEquals("Test title", productQuestionRequestDto.getTitle());
    }
}
