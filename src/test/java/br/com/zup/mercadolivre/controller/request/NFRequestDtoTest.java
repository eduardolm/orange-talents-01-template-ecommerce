package br.com.zup.mercadolivre.controller.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NFRequestDtoTest {
    @Test
    public void testToString() {
        assertEquals("NFRequestDto{purchaseId=null, customerId=null}", (new NFRequestDto()).toString());
    }
}

