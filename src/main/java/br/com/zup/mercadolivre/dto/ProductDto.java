package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductCharacteristics;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDto {

    private Long id;
    private String name;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private String category;
    private String productOwner;
    private Map<String, String> characteristics;

    @Deprecated
    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory().getName();
        this.productOwner = product.getProductOwner().getEmail();
        this.characteristics = product.getCharacteristics()
                .stream()
                .collect(Collectors.toMap(ProductCharacteristics::getName, ProductCharacteristics::getDescription));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getProductOwner() {
        return productOwner;
    }

    public Map<String, String> getCharacteristics() {
        return characteristics;
    }
}
