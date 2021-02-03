package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductCharacteristics;

import javax.validation.constraints.NotBlank;

public class CharacteristicsRequestDto {

    @NotBlank(message = "O nome da carcterística é obrigatório.")
    private String name;

    @NotBlank(message = "A descrição é obrigatória.")
    private String description;

    public CharacteristicsRequestDto(@NotBlank(message = "O nome da carcterística é obrigatório.") String name,
                                     @NotBlank(message = "A descrição é obrigatória.") String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CharacteristicsRequestDto{" +
                "Nome:'" + name + '\'' +
                ", Descrição:'" + description + '\'' +
                '}';
    }

    public ProductCharacteristics toModel(Product product) {
        return new ProductCharacteristics(name, description, product);
    }
}
