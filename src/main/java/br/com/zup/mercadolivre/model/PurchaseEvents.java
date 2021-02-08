package br.com.zup.mercadolivre.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PurchaseEvents {

    @Autowired
    private Set<PurchaseSuccessEvent> purchaseSuccessEvent;

    public void process(Purchase purchase) {
        if (purchase.successfullyProcessed()) {
            purchaseSuccessEvent.forEach(event -> event.process(purchase));
        }
        else {
            // codigo
        }
    }
}
