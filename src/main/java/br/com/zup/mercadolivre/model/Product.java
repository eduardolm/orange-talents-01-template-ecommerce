package br.com.zup.mercadolivre.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private Integer quantity;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User productOwner;

    public Product(String name, Integer quantity, String description, BigDecimal price, Category category, User productOwner) {
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.category = category;
        this.productOwner = productOwner;
    }

    @Deprecated
    public Product() {

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

    public User getProductOwner() {
        return productOwner;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", Nome:'" + name + '\'' +
                ", Quantidade:" + quantity +
                ", Descrição:'" + description + '\'' +
                ", Preço:" + price +
                ", Categoria:" + category.getName() +
                ", Usuário:'" + productOwner + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (!getName().equals(product.getName())) return false;
        if (getQuantity() != null ? !getQuantity().equals(product.getQuantity()) : product.getQuantity() != null)
            return false;
        if (!getDescription().equals(product.getDescription())) return false;
        if (!getPrice().equals(product.getPrice())) return false;
        if (!getCategory().equals(product.getCategory())) return false;
        return getProductOwner().equals(product.getProductOwner());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getName().hashCode();
        result = prime * result + (getQuantity() != null ? getQuantity().hashCode() : 0);
        result = prime * result + getDescription().hashCode();
        result = prime * result + getPrice().hashCode();
        result = prime * result + getCategory().hashCode();
        result = prime * result + getProductOwner().hashCode();
        return result;
    }
}
