package br.com.zup.mercadolivre.utils.builder;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import com.fasterxml.jackson.databind.PropertyMetadata;

import java.math.BigDecimal;

public class ProductBuilder {

    private String name;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private Category category;

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public Product build() {
        return new Product(name, quantity, description, price, category);
    }
}
