package br.com.zup.mercadolivre.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "perguntas_produtos")
public class ProductQuestion implements Comparable<ProductQuestion> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    private User interested;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Deprecated
    public ProductQuestion() {
    }

    public ProductQuestion(String title, User interested, Product product) {
        this.title = title;
        this.interested = interested;
        this.product = product;
        this.createdAt = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Question{" +
                "Id:" + id +
                ", Título:'" + title + '\'' +
                ", Interessado:" + interested.getEmail() +
                ", Produto:" + product.getName() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getInterested() {
        return interested;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductQuestion)) return false;

        ProductQuestion productQuestion = (ProductQuestion) o;

        if (getTitle() != null ? !getTitle().equals(productQuestion.getTitle()) : productQuestion.getTitle() != null) return false;
        if (getInterested() != null ? !getInterested().equals(productQuestion.getInterested()) : productQuestion.getInterested() != null)
            return false;
        return Objects.equals(product, productQuestion.product);
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getInterested() != null ? getInterested().hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(ProductQuestion o) {
        return this.title.compareTo(o.title);
    }
}
