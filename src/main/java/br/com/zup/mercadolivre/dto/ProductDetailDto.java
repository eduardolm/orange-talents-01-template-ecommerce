package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDetailDto {

    private Long id;
    private String name;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private Category category;
    private UserDto productOwner;
    private Set<ProductCharacteristicsDto> characteristics;

    public ProductDetailDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.productOwner = new UserDto(product.getProductOwner());
        this.characteristics = product.getCharacteristics().stream().map(ProductCharacteristicsDto::new).collect(Collectors.toSet());
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

    public Category getCategory() {
        return category;
    }

    public UserDto getProductOwner() {
        return productOwner;
    }

    public Set<ProductCharacteristicsDto> getCharacteristics() {
        return characteristics;
    }
}
