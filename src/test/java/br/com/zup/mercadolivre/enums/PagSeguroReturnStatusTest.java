package br.com.zup.mercadolivre.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagSeguroReturnStatusTest {
    @Test
    public void testNormalize() {
        assertEquals(TransactionStatus.sucesso, PagSeguroReturnStatus.SUCESSO.normalize());
        assertEquals(TransactionStatus.erro, PagSeguroReturnStatus.ERRO.normalize());
    }
}

