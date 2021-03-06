package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.controller.request.PaymentGatewayResponseDto;
import br.com.zup.mercadolivre.enums.PaymentGateway;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<>();

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
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", customer=" + customer +
                ", paymentGateway=" + paymentGateway +
                ", transactions=" + transactions +
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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public String redirectUrl(UriComponentsBuilder uriComponentsBuilder) {
        return this.paymentGateway.createRedirectUrl(this, uriComponentsBuilder);
    }

    public void addTransaction(@Valid PaymentGatewayResponseDto request) {
        Transaction newTransaction = request.toTransaction(this);

        Assert.state(!this.transactions.contains(newTransaction), "Já existe uma transação igual a essa " +
                "processada: " + newTransaction.toString());

        Set<Transaction> successfullyFinishedTransactions = successfullyFinishedTransactions();

        Assert.state(!this.successfullyProcessed(), "Essa compra já foi concluída com sucesso.");

        this.transactions.add(newTransaction);
    }

    private Set<Transaction> successfullyFinishedTransactions() {
        Set<Transaction> successfullyFinishedTransactions = this.transactions
                .stream().filter(Transaction::finishedSuccessfully).collect(Collectors.toSet());

        Assert.isTrue(successfullyFinishedTransactions.size() <= 1, "Existe mais de uma transação " +
                "concluída com sucesso.");

        return successfullyFinishedTransactions;
    }

    public boolean successfullyProcessed() {
        return !successfullyFinishedTransactions().isEmpty();
    }
}
