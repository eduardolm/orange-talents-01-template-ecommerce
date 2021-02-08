package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.utils.builder.TestBuilders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

public class PurchaseEventsTest {

    @Test
    @DisplayName("Should trigger success events")
    public void test1() throws Exception {
        Purchase purchase = TestBuilders.PurchaseFixture.newPurchase().finished();
        PurchaseSuccessEvent successEvent = Mockito.mock(PurchaseSuccessEvent.class);
        Set<PurchaseSuccessEvent> events = Set.of(successEvent);
        PurchaseEvents purchaseEvents = new PurchaseEvents();
        purchaseEvents.setPurchaseSuccessEvent(events);

        purchaseEvents.process(purchase);

        Mockito.verify(successEvent).process(purchase);
    }

    @Test
    @DisplayName("Should trigger failure events")
    public void test2() throws Exception {
        Purchase purchase = TestBuilders.PurchaseFixture.newPurchase().paymentFailure();
        PurchaseSuccessEvent successEvent = Mockito.mock(PurchaseSuccessEvent.class);
        Set<PurchaseSuccessEvent> events = Set.of(successEvent);
        PurchaseEvents purchaseEvents = new PurchaseEvents();
        purchaseEvents.setPurchaseSuccessEvent(events);

        purchaseEvents.process(purchase);

        Mockito.verify(successEvent, Mockito.never()).process(purchase);
    }
}
