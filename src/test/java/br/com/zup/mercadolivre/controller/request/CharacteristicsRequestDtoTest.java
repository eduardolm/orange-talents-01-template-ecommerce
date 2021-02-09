package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductCharacteristics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacteristicsRequestDtoTest {
    @Test
    public void shouldToStringRepresentCustomizedImplementation() {
        assertEquals("CharacteristicsRequestDto{Nome:'Name', Descrição:'The characteristics of someone or something'}",
                (new CharacteristicsRequestDto("Name", "The characteristics of someone or something")).toString());
    }

    @Test
    public void testToString() {
        assertEquals("CharacteristicsRequestDto{Nome:'Name', Descrição:'The characteristics of someone or something'}",
                (new CharacteristicsRequestDto("Name", "The characteristics of someone or something")).toString());
    }

    @Test
    public void testEquals() {
        assertNotEquals((new CharacteristicsRequestDto("Name", "The characteristics of someone or something")), "obj");
    }

    @Test
    public void testEquals2() {
        CharacteristicsRequestDto characteristicsRequestDto = new CharacteristicsRequestDto("Name",
                "The characteristics of someone or something");

        assertEquals(new CharacteristicsRequestDto("Name", "The characteristics of someone or something"), characteristicsRequestDto);
    }

    @Test
    public void testEquals3() {
        CharacteristicsRequestDto characteristicsRequestDto = new CharacteristicsRequestDto(
                "The characteristics of someone or something", "The characteristics of someone or something");

        assertNotEquals(new CharacteristicsRequestDto("Name", "The characteristics of someone or something"), characteristicsRequestDto);
    }

    @Test
    public void testHashCode() {
        assertEquals(1334610322,
                (new CharacteristicsRequestDto("Name", "The characteristics of someone or something")).hashCode());
    }

    @Test
    public void shouldToModelCreateProductCharacteristicsOutOfDto() {
        CharacteristicsRequestDto characteristicsRequestDto = new CharacteristicsRequestDto("Name",
                "The characteristics of someone or something");

        ProductCharacteristics actualToModelResult = characteristicsRequestDto.toModel(new Product());

        assertEquals("Name", actualToModelResult.getName());
        assertEquals("The characteristics of someone or something", actualToModelResult.getDescription());
        assertEquals("ProductCharacteristics{Id:null, Nome:'Name', Descrição:'The characteristics of someone or something'}",
                actualToModelResult.toString());
    }

    @Test
    public void testToModel() {
        // Arrange
        CharacteristicsRequestDto characteristicsRequestDto = new CharacteristicsRequestDto("Name",
                "The characteristics of someone or something");
        Product product = new Product();

        // Act
        ProductCharacteristics actualToModelResult = characteristicsRequestDto.toModel(product);

        // Assert
        assertEquals("Name", actualToModelResult.getName());
        assertEquals("The characteristics of someone or something", actualToModelResult.getDescription());
        assertSame(product, actualToModelResult.getProduct());
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

