package br.com.zup.mercadolivre.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PurchaseRequestDto {

    @Positive(message = "O valor deve ser positivo.")
    private int quantity;

    @NotNull(message = "Obrigatório informar o id do produto.")
    private Long productId;

    public PurchaseRequestDto(@Positive(message = "O valor deve ser positivo.") int quantity,
                              @NotNull(message = "Obrigatório informar o id do produto.") Long prooductId) {

        this.quantity = quantity;
        this.productId = prooductId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }
}
