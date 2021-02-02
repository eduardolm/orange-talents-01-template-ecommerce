package br.com.zup.mercadolivre.validator;

import br.com.zup.mercadolivre.controller.request.ProductRequestDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class SameNameCharacteristicsValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductRequestDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        ProductRequestDto request = (ProductRequestDto) target;
        Set<String> repeatedNames = request.findRepeatedCharacteristics();
        if (!repeatedNames.isEmpty()) {
            errors.rejectValue("characteristics", null, "Existem características iguais " + repeatedNames);
        }
    }
}
