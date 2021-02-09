package br.com.zup.mercadolivre.utils.builder;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.controller.request.ProductRequestDto;

import java.math.BigDecimal;
import java.util.List;

public class ProductRequestDtoBuilder {

    private String name;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private List<CharacteristicsRequestDto> characteristics;

    public ProductRequestDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductRequestDtoBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductRequestDtoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductRequestDtoBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductRequestDtoBuilder withCategory(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public ProductRequestDtoBuilder withCharacteristcs(List<CharacteristicsRequestDto> characteristics) {
        this.characteristics = characteristics;
        return this;
    }

    public ProductRequestDto build() {
        return new ProductRequestDto(name, quantity, description, price, categoryId, characteristics);
    }
}
