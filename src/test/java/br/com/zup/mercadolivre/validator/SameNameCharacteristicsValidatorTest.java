package br.com.zup.mercadolivre.validator;

import br.com.zup.mercadolivre.controller.request.ProductRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SameNameCharacteristicsValidatorTest {
    @Test
    public void testSupports() {
        SameNameCharacteristicsValidator sameNameCharacteristicsValidator = new SameNameCharacteristicsValidator();

        assertFalse(sameNameCharacteristicsValidator.supports(Object.class));
    }

    @Test
    public void testSupports2() {
        SameNameCharacteristicsValidator sameNameCharacteristicsValidator = new SameNameCharacteristicsValidator();

        assertTrue(sameNameCharacteristicsValidator.supports(ProductRequestDto.class));
    }
}

