package br.com.zup.mercadolivre.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Obrigatório informar o produto associado à imagem.")
    @Valid
    private Product product;

    @URL(message = "Formato URL inválido.")
    @NotBlank(message = "Link para a imagem não pode ser vazio.")
    private String link;

    @Deprecated
    public ProductImage() {}

    public ProductImage(@NotNull @Valid Product product, @URL @NotBlank String link) {
        this.product = product;
        this.link = link;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", link='" + link + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ProductImage)) return false;

        ProductImage that = (ProductImage) obj;

        if (!Objects.equals(product, that.product)) return false;
        return Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        int result = product != null ? product.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getLink() {
        return link;
    }
}
