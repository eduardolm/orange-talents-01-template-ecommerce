package br.com.zup.mercadolivre.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductCharacteristics;
import org.junit.jupiter.api.Test;

public class CharacteristicsRequestDtoTest {
    @Test
    public void shouldToStringRepresentCustomizedImplementation() {
        assertEquals("CharacteristicsRequestDto{Nome:'Name', Descrição:'The characteristics of someone or something'}",
                (new CharacteristicsRequestDto("Name", "The characteristics of someone or something")).toString());
    }

    @Test
    public void shouldToModelCreateProductCharacteristicsOutOfDto() {
        CharacteristicsRequestDto characteristicsRequestDto = new CharacteristicsRequestDto("Name",
                "The characteristics of someone or something");

        ProductCharacteristics actualToModelResult = characteristicsRequestDto.toModel(new Product());

        assertEquals("Name", actualToModelResult.getName());
        assertEquals("The characteristics of someone or something", actualToModelResult.getDescription());
        assertEquals("ProductCharacteristics{Id:null, Nome:'Name', Descrição:'The characteristics of " +
                "someone or something', Produto:Produto{Id:null, nome:'null', Quantidade:null, Descrição:'null', " +
                "Preço:null, Categoria:null, Proprietário:null, Características:{}, Imagens:[]}}",
                actualToModelResult.toString());
    }

    @Test
    public void shouldGettersReturnCorrectValues() {
        CharacteristicsRequestDto characteristicsRequestDto = new CharacteristicsRequestDto("Name",
                "The characteristics of someone or something");

        ProductCharacteristics actualToModelResult = characteristicsRequestDto.toModel(new Product());

        assertEquals("Name", actualToModelResult.getName());
        assertEquals("The characteristics of someone or something", actualToModelResult.getDescription());

    }
}

