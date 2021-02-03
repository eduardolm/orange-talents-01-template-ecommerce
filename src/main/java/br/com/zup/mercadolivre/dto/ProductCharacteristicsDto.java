package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductCharacteristics;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductCharacteristicsDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Valid
    @ManyToOne
    private Product product;

    @Deprecated
    public ProductCharacteristicsDto() {}

    public ProductCharacteristicsDto(@NotBlank String name,
                                 @NotBlank String description,
                                 @NotNull @Valid Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

    public ProductCharacteristics toModel(Product product) {
        return new ProductCharacteristics(name, description, product);
    }
}
