package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.enums.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    @Test
    public void testConstructor() {
        Purchase purchase = new Purchase();

        Transaction actualTransaction = new Transaction(TransactionStatus.sucesso, "42", purchase);

        assertSame(purchase, actualTransaction.getPurchase());
        assertEquals("42", actualTransaction.getGatewayTransactionId());
        assertEquals(TransactionStatus.sucesso, actualTransaction.getStatus());
    }

    @Test
    public void testToString() {
        assertEquals("Transaction{id=null, status=null, gatewayTransactionId='null', createdAt=null}",
                (new Transaction()).toString());
    }

    @Test
    public void testFinishedSuccessfully() {
        assertTrue((new Transaction(TransactionStatus.sucesso, "42", new Purchase())).finishedSuccessfully());
        assertFalse((new Transaction(TransactionStatus.erro, "42", new Purchase())).finishedSuccessfully());
    }

    @Test
    public void testEquals() {
        assertNotEquals((new Transaction()), "o");
    }

    @Test
    public void testEquals2() {
        Transaction transaction = new Transaction();

        assertEquals(new Transaction(), transaction);
    }

    @Test
    public void testEquals3() {
        Transaction transaction = new Transaction();

        assertNotEquals(new Transaction(TransactionStatus.sucesso, "42", new Purchase()), transaction);
    }

    @Test
    public void testEquals4() {
        Transaction transaction = new Transaction(TransactionStatus.sucesso, "42", new Purchase());

        assertNotEquals(new Transaction(), transaction);
    }

    @Test
    public void testHashCode() {
        assertEquals(0, (new Transaction()).hashCode());
        assertEquals(1662, (new Transaction(TransactionStatus.sucesso, "42", new Purchase())).hashCode());
    }
}

