package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.enums.TransactionStatus;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaypalRequestDtoTest {
    @Test
    public void testToString() {
        assertEquals("PaypalRequestDto{transactionId='null', status=0}", (new PaypalRequestDto()).toString());
    }

    @Test
    public void testToTransaction() {
        PaypalRequestDto paypalRequestDto = new PaypalRequestDto();
        Purchase purchase = new Purchase();

        Transaction actualToTransactionResult = paypalRequestDto.toTransaction(purchase);

        assertSame(purchase, actualToTransactionResult.getPurchase());
        assertNull(actualToTransactionResult.getGatewayTransactionId());
        assertEquals(TransactionStatus.erro, actualToTransactionResult.getStatus());
    }

    @Test
    public void testToTransaction2() {
        PaypalRequestDto paypalRequestDto = new PaypalRequestDto("42", 1);
        Purchase purchase = new Purchase();

        Transaction actualToTransactionResult = paypalRequestDto.toTransaction(purchase);

        assertSame(purchase, actualToTransactionResult.getPurchase());
        assertEquals("42", actualToTransactionResult.getGatewayTransactionId());
        assertEquals(TransactionStatus.sucesso, actualToTransactionResult.getStatus());
    }
}

