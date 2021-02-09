package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.enums.TransactionStatus;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Transaction;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PaypalRequestDto implements PaymentGatewayResponseDto{

    @NotBlank(message = "Obrigatório informar o id da transação.")
    private String transactionId;

    @NotNull(message = "Status é obrigatório.")
    @Min(value = 0, message = "Status mínimo é 0.")
    @Max(value = 1, message = "Status máximo é 1.")
    private int status;

    public PaypalRequestDto(@NotBlank(message = "Obrigatório informar o id da transação.") String transactionId,
                               @NotNull(message = "Status é obrigatório.") int status) {

        super();
        this.transactionId = transactionId;
        this.status = status;
    }

    @Deprecated
    public PaypalRequestDto() {
    }

    @Override
    public String toString() {
        return "PaypalRequestDto{" +
                "transactionId='" + transactionId + '\'' +
                ", status=" + status +
                '}';
    }

    public Transaction toTransaction(Purchase purchase) {
        TransactionStatus tempStatus = this.status == 0 ? TransactionStatus.erro : TransactionStatus.sucesso;
        return new Transaction(tempStatus, transactionId, purchase);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getStatus() {
        return status;
    }
}
