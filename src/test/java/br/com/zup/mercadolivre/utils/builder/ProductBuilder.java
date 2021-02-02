package br.com.zup.mercadolivre.utils.builder;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;

import java.math.BigDecimal;

public class ProductBuilder {

    private String name;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private Category category;
    private User productOwner;

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

    public ProductBuilder withProductOwner(User user) {
        this.productOwner = user;
        return this;
    }

    public Product build() {
        return new Product(name, quantity, description, price, category, productOwner);
    }
}
