package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public interface PaymentGatewayResponseDto {

    /**
     *
     * @param purchase
     * @return -> new transaction based on specific payment gateway
     */
    Transaction toTransaction(Purchase purchase);
}
