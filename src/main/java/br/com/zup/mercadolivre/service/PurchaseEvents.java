package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;
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

    public Set<PurchaseSuccessEvent> getPurchaseSuccessEvent() {
        return purchaseSuccessEvent;
    }

    public void setPurchaseSuccessEvent(Set<PurchaseSuccessEvent> purchaseSuccessEvent) {
        this.purchaseSuccessEvent = purchaseSuccessEvent;
    }
}
