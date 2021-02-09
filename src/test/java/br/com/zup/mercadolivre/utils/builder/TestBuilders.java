package br.com.zup.mercadolivre.utils.builder;

import br.com.zup.mercadolivre.controller.request.CharacteristicsRequestDto;
import br.com.zup.mercadolivre.controller.request.PaymentGatewayResponseDto;
import br.com.zup.mercadolivre.enums.PaymentGateway;
import br.com.zup.mercadolivre.enums.TransactionStatus;
import br.com.zup.mercadolivre.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class TestBuilders {

    public static class PurchaseFixture {
        private Purchase purchase;

        public PurchaseFixture(Purchase purchase) {
            super();
            this.purchase = purchase;
        }

        public Purchase finished() {
            PaymentGatewayResponseDto successResponse1 = (purchase) ->
                    new Transaction(TransactionStatus.sucesso, "1", purchase);
            this.purchase.addTransaction(successResponse1);
            return purchase;
        }

        public Purchase paymentFailure() {
            PaymentGatewayResponseDto failureResponse = (purchase) ->
                    new Transaction(TransactionStatus.erro, "1", purchase);
            this.purchase.addTransaction(failureResponse);
            return purchase;
        }

        public static PurchaseFixture newPurchase() {
            Category category = new Category("test");
            User owner = new User("email@email.com", "pass1234");
            Collection<CharacteristicsRequestDto> characteristics = new ArrayList<>();
            characteristics.add(new CharacteristicsRequestDto("name", "description"));
            characteristics.add(new CharacteristicsRequestDto("name1", "description1"));
            characteristics.add(new CharacteristicsRequestDto("name2", "description2"));

            Product product = new ProductBuilder()
                    .withName("Test product")
                    .withQuantity(100)
                    .withDescription("description")
                    .withPrice(BigDecimal.TEN)
                    .withCategory(category)
                    .withCharacteristics(characteristics)
                    .withProductOwner(owner)
                    .build();

            User customer = new User("customer@email.com", "pass1234");

            PaymentGateway paymentGateway = PaymentGateway.PAG_SEGURO;

            return new PurchaseFixture(new Purchase(product, 50, customer, paymentGateway));
        }
    }
}
