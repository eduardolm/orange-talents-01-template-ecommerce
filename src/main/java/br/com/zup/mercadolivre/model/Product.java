package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.dto.ProductCharacteristicsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<ProductCharacteristics> characteristics = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<ProductImage> images = new HashSet<>();

    public Product(String name,
                   Integer quantity,
                   String description,
                   BigDecimal price,
                   Category category,
                   User productOwner,
                   @Valid Collection<CharacteristicsRequestDto> characteristics) {

        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.category = category;
        this.productOwner = productOwner;
        Set<ProductCharacteristics> newCharacteristics = characteristics
                .stream().map(characteristic -> characteristic.toModel(this))
                .collect(Collectors.toSet());
        this.characteristics.addAll(newCharacteristics);

        Assert.isTrue(this.characteristics.size() >= 3, "Todo produto precisa ter no mínimo 3 características.");
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

    @JsonIgnore
    public Set<ProductCharacteristics> getCharacteristics() {
        return characteristics;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id" + id +
                ", Nome:'" + name + '\'' +
                ", Quantidade:" + quantity +
                ", Descrição:'" + description + '\'' +
                ", Preço:" + price +
                ", Categoria:" + category +
                ", DonoProduto:" + productOwner +
                ", Características:" + getCharacteristics().stream()
                .collect(Collectors.toMap(ProductCharacteristics::getName, ProductCharacteristics::getDescription)) +
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
        return getProductOwner().equals(product.getProductOwner());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getQuantity() != null ? getQuantity().hashCode() : 0);
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getProductOwner().hashCode();
        return result;
    }

    public void associateImages(Set<String> links) {
        Set<ProductImage> images = links.stream()
                .map(link -> new ProductImage(this, link))
                .collect(Collectors.toSet());

        this.images.addAll(images);
    }
}
