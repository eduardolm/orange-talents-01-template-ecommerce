package br.com.zup.mercadolivre.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenDtoTest {

    @Test
    public void shouldConstructorCreateTokenDtoInstance(){

        TokenDto tokenDto = new TokenDto("ldsgdgjskjg;lsj;ljg");

        assertTrue(tokenDto instanceof TokenDto);
    }

    @Test
    public void shouldGetterWorkAsExpected(){

        TokenDto tokenDto = new TokenDto("ldsgdgjskjg;lsj;ljg");

        assertEquals("ldsgdgjskjg;lsj;ljg", tokenDto.getToken());
    }
}
