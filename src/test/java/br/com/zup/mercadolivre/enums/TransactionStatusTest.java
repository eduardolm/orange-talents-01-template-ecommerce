package br.com.zup.mercadolivre.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionStatusTest {

    @Test
    public void testValueOf2() {
        assertEquals(TransactionStatus.erro, TransactionStatus.valueOf("erro"));
    }

    @Test
    public void testValues() {
        assertEquals(2, TransactionStatus.values().length);
    }
}

