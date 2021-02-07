package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.enums.PaymentGateway;
import org.springframework.web.util.UriComponentsBuilder;

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

    @Enumerated
    @Column(nullable = false)
    private PaymentGateway paymentGateway;

    public Purchase(Product product, int quantity, User customer, PaymentGateway paymentGateway) {
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
        this.paymentGateway = paymentGateway;
    }

    @Deprecated
    public Purchase() {
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "Id:" + id +
                ", Produto:" + product +
                ", Quantidade:" + quantity +
                ", Comprador:" + customer +
                ", Pagamento:" + paymentGateway +
                '}';
    }

    public Long getId() {
        return id;
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

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public String redirectUrl(UriComponentsBuilder uriComponentsBuilder) {
        return this.paymentGateway.createRedirectUrl(this, uriComponentsBuilder);
    }
}
