package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.enums.PaymentGateway;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.Purchase;

public class PurchaseDto {

    private Long id;
    private ProductDto product;
    private int quantity;
    private PaymentGateway paymentGateway;

    public PurchaseDto(Long id, Product product, int quantity, PaymentGateway paymentGateway) {
        this.id = id;
        this.product = new ProductDto(product);
        this.quantity = quantity;
        this.paymentGateway = paymentGateway;
    }

    public PurchaseDto(Purchase purchase) {
        this.id = purchase.getId();
        this.product = new ProductDto(purchase.getProduct());
        this.quantity = purchase.getQuantity();
        this.paymentGateway = purchase.getPaymentGateway();
    }

    public Long getId() {
        return id;
    }

    public ProductDto getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "Id:" + id +
                ", Produto:" + product +
                ", Quantidade:" + quantity +
                ", Pagamento:" + paymentGateway +
                '}';
    }
}
