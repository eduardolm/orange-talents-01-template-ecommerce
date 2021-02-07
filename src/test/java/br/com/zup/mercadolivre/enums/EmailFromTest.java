package br.com.zup.mercadolivre.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmailFromTest {
    @Test
    public void testLabel() {

        assertEquals("novapergunta@mercadolivre.com.br", EmailFrom.QUESTION.label);
        assertEquals("vendas@mercadolivre.com.br", EmailFrom.PURCHASE.label);
    }

    @Test
    public void testValueOf2() {
        assertEquals(EmailFrom.PURCHASE, EmailFrom.valueOf("PURCHASE"));
    }

    @Test
    public void testValues() {
        assertEquals(2, EmailFrom.values().length);
    }
}

