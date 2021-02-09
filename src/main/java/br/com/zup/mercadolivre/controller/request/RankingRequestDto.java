package br.com.zup.mercadolivre.controller.request;

import javax.validation.constraints.NotNull;

public class RankingRequestDto {

    @NotNull(message = "Obrigatório informar o id da compra.")
    private Long purchaseId;

    @NotNull(message = "Obrigatório informar o id do vendedor.")
    private Long sellerId;

    public RankingRequestDto(@NotNull(message = "Obrigatório informar o id da compra.") Long purchaseId,
                             @NotNull(message = "Obrigatório informar o id do vendedor.") Long sellerId) {
        this.purchaseId = purchaseId;
        this.sellerId = sellerId;
    }

    @Deprecated
    public RankingRequestDto() {
    }

    @Override
    public String toString() {
        return "RankingRequestDto{" +
                "purchaseId=" + purchaseId +
                ", sellerId=" + sellerId +
                '}';
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getSellerId() {
        return sellerId;
    }
}
