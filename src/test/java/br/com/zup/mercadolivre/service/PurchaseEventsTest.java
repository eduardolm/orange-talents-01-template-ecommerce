package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.utils.builder.TestBuilders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PurchaseEventsTest {

    @Autowired
    private PurchaseEvents purchaseEvents;

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
    public void testSetPurchaseSuccessEvent() {
        HashSet<PurchaseSuccessEvent> purchaseSuccessEventSet = new HashSet<>();

        this.purchaseEvents.setPurchaseSuccessEvent(purchaseSuccessEventSet);

        assertSame(purchaseSuccessEventSet, this.purchaseEvents.getPurchaseSuccessEvent());
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
