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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CharacteristicsRequestDto)) return false;

        CharacteristicsRequestDto that = (CharacteristicsRequestDto) obj;

        if (!getName().equals(that.getName())) return false;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }

    public ProductCharacteristics toModel(Product product) {
        return new ProductCharacteristics(name, description, product);
    }
}
