package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Product;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;
    private String name;
    private Integer quantity;
    private String description;
    private BigDecimal price;

    @Deprecated
    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.price = product.getPrice();
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

    @Override
    public String toString() {
        return "ProductDto{" +
                "Id:" + id +
                ", Nome:'" + name + '\'' +
                ", Quantidade:" + quantity +
                ", Descrição:'" + description + '\'' +
                ", Preço:" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto)) return false;

        ProductDto that = (ProductDto) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
