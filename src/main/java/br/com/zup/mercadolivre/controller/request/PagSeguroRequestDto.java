package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.enums.PagSeguroReturnStatus;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Transaction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagSeguroRequestDto {

    @NotBlank(message = "Obrigatório informar o id da transação.")
    private String transactionId;

    @NotNull(message = "Status é obrigatório.")
    private PagSeguroReturnStatus status;

    public PagSeguroRequestDto(@NotBlank(message = "Obrigatório informar o id da transação.") String transactionId,
                               @NotNull(message = "Status é obrigatório.") PagSeguroReturnStatus status) {

        this.transactionId = transactionId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PagSeguroRequestDto{" +
                "transactionId='" + transactionId + '\'' +
                ", status=" + status +
                '}';
    }

    public Transaction toTransaction(Purchase purchase) {
        return new Transaction(status.normalize(), transactionId, purchase);
    }
}
