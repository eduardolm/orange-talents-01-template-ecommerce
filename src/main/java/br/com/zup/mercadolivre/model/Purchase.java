package br.com.zup.mercadolivre.model;

import javax.persistence.*;

@Entity
@Table(name = "compras")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    private User customer;

    public Purchase(Product product, int quantity, User customer) {
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
    }

    @Deprecated
    public Purchase() {
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "Id:" + id +
                ", Produto:" + product.getName() +
                ", Quantidade:" + quantity +
                ", Comprador:" + customer.getEmail() +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public User getCustomer() {
        return customer;
    }
}
