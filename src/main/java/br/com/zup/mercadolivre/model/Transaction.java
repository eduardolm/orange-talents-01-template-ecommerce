package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.enums.TransactionStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private String gatewayTransactionId;
    private LocalDateTime createdAt;

    @ManyToOne
    private Purchase purchase;

    public Transaction(@NotNull TransactionStatus status,
                       @NotBlank(message = "Obrigatório informar o id da transação.") String gatewayTransactionId,
                       Purchase purchase) {

        this.status = status;
        this.gatewayTransactionId = gatewayTransactionId;
        this.createdAt = LocalDateTime.now();
        this.purchase = purchase;
    }

    public boolean finishedSuccessfully() {
        return this.status.equals(TransactionStatus.sucesso);
    }

    @Deprecated
    public Transaction() {
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", status=" + status +
                ", gatewayTransactionId='" + gatewayTransactionId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        return getGatewayTransactionId() != null ? getGatewayTransactionId().equals(that.getGatewayTransactionId()) : that.getGatewayTransactionId() == null;
    }

    @Override
    public int hashCode() {
        return getGatewayTransactionId() != null ? getGatewayTransactionId().hashCode() : 0;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getGatewayTransactionId() {
        return gatewayTransactionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Purchase getPurchase() {
        return purchase;
    }
}
