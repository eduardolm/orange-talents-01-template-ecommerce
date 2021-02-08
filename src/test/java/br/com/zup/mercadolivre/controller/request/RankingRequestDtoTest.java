package br.com.zup.mercadolivre.controller.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingRequestDtoTest {
    @Test
    public void testToString() {
        assertEquals("RankingRequestDto{purchaseId=null, sellerId=null}", (new RankingRequestDto()).toString());
    }
}

