package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.enums.PaymentGateway;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PurchaseRequestDto {

    @Positive(message = "O valor deve ser positivo.")
    private int quantity;

    @NotNull(message = "Obrigatório informar o id do produto.")
    private Long productId;

    @NotNull(message = "A escolha da forma de pagamento é obrigatória.")
    private PaymentGateway gateway;

    public PurchaseRequestDto(@Positive(message = "O valor deve ser positivo.") int quantity,
                              @NotNull(message = "Obrigatório informar o id do produto.") Long prooductId,
                              @NotNull(message = "A escolha da forma de pagamento é obrigatória.") PaymentGateway gateway) {

        this.quantity = quantity;
        this.productId = prooductId;
        this.gateway = gateway;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }
}
