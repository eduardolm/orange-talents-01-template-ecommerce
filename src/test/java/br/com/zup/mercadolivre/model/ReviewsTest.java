package br.com.zup.mercadolivre.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewsTest {
    @Test
    public void testAverage() {
        assertEquals(0.0, (new Reviews(new HashSet<>())).average());
    }
}

