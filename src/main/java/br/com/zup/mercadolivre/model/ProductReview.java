package br.com.zup.mercadolivre.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "opiniao_produtos")
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer grade;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User customer;

    public ProductReview(Integer grade, String title, String description, Product product, User customer) {
        this.grade = grade;
        this.title = title;
        this.description = description;
        this.product = product;
        this.customer = customer;
    }

    @Deprecated
    public ProductReview() {}

    public Integer getGrade() {
        return grade;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "Id:" + id +
                ", Nota:" + grade +
                ", Titulo:'" + title + '\'' +
                ", Descrição:'" + description + '\'' +
                ", Produto:" + product +
                ", Cliente:" + customer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductReview)) return false;

        ProductReview that = (ProductReview) o;

        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (!Objects.equals(product, that.product)) return false;
        return Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }
}
