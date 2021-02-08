package br.com.zup.mercadolivre.controller.request;

import javax.validation.constraints.NotNull;

public class NFRequestDto {

    @NotNull(message = " Obrigatório informar o id de compra.")
    private Long purchaseId;

    @NotNull(message = "Obrigatório informar o id do comprador.")
    private Long customerId;

    public NFRequestDto(@NotNull(message = " Obrigatório informar o id de compra.") Long purchaseId,
                        @NotNull(message = "Obrigatório informar o id do comprador.") Long customerId) {
        this.purchaseId = purchaseId;
        this.customerId = customerId;
    }

    @Deprecated
    public NFRequestDto() {
    }

    @Override
    public String toString() {
        return "NFRequestDto{" +
                "purchaseId=" + purchaseId +
                ", customerId=" + customerId +
                '}';
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
