package br.com.zup.mercadolivre.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductCharacteristic {
    public ProductCharacteristic(@NotBlank String name,
                                 @NotBlank String description,
                                 @NotNull @Valid Product product) {
    }
}
