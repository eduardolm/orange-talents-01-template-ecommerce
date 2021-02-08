package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.enums.PagSeguroReturnStatus;
import br.com.zup.mercadolivre.enums.TransactionStatus;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PagSeguroRequestDtoTest {
    @Test
    public void testToString() {
        assertEquals("PagSeguroRequestDto{transactionId='null', status=null}", (new PagSeguroRequestDto()).toString());
    }

    @Test
    public void testToTransaction() {
        PagSeguroRequestDto pagSeguroRequestDto = new PagSeguroRequestDto("42", PagSeguroReturnStatus.SUCESSO);
        Purchase purchase = new Purchase();

        Transaction actualToTransactionResult = pagSeguroRequestDto.toTransaction(purchase);

        assertSame(purchase, actualToTransactionResult.getPurchase());
        assertEquals("42", actualToTransactionResult.getGatewayTransactionId());
        assertEquals(TransactionStatus.sucesso, actualToTransactionResult.getStatus());
    }
}

