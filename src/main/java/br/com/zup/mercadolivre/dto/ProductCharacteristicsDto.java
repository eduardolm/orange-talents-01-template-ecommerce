package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.ProductCharacteristics;

public class ProductCharacteristicsDto {

    private Long id;
    private String name;
    private String description;


    @Deprecated
    public ProductCharacteristicsDto() {}

    public ProductCharacteristicsDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ProductCharacteristicsDto(ProductCharacteristics productCharacteristics) {
        this.id = productCharacteristics.getId();
        this.name = productCharacteristics.getName();
        this.description = productCharacteristics.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
