package br.com.zup.mercadolivre.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "carateristicas_produtos")
public class ProductCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Valid
    @ManyToOne
    private Product product;

    @Deprecated
    public ProductCharacteristics() {
    }

    public ProductCharacteristics(@NotBlank String name,
                                  @NotBlank String description,
                                  @NotNull @Valid Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "ProductCharacteristics{" +
                "Id:" + id +
                ", Nome:'" + name + '\'' +
                ", Descrição:'" + description + '\'' +
                ", Produto:" + product +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCharacteristics)) return false;

        ProductCharacteristics that = (ProductCharacteristics) o;

        if (!getName().equals(that.getName())) return false;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }
}
