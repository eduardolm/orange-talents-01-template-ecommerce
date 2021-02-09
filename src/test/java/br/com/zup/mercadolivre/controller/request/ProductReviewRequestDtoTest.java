package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductReview;
import br.com.zup.mercadolivre.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductReviewRequestDtoTest {
    @Test
    public void testToModel() {
        ProductReviewRequestDto productReviewRequestDto = new ProductReviewRequestDto(1, "Super",
                "Test description");
        Product product = new Product();

        ProductReview actualToModelResult = productReviewRequestDto.toModel(product, new User());

        assertEquals(1, actualToModelResult.getGrade().intValue());
        assertEquals("Super", actualToModelResult.getTitle());
        assertEquals("Test description", actualToModelResult.getDescription());
        assertEquals("ProductReview{Id:null, Nota:1, Titulo:'Super', Descrição:'Test description', " +
                "Produto:Produto{Id=null, Nome:'null', Quantidade:null, Descrição:'null', Preço:null, " +
                "Categoria:null, Proprietário:null, Características:{}, Imagens:[]}, Cliente:Usuário{id:null, " +
                "e-mail:'null', Cadastrado em:null}}", actualToModelResult.toString());
    }

    @Test
    public void testToModel2() {
        ProductReviewRequestDto productReviewRequestDto = new ProductReviewRequestDto(1, "Dr",
                "The characteristics of someone or something");
        Product product = new Product();

        ProductReview actualToModelResult = productReviewRequestDto.toModel(product, new User());

        assertEquals(1, actualToModelResult.getGrade().intValue());
        assertEquals("Dr", actualToModelResult.getTitle());
        assertEquals("The characteristics of someone or something", actualToModelResult.getDescription());
        assertEquals("ProductReview{Id:null, Nota:1, Titulo:'Dr', Descrição:'The characteristics of someone or something',"
                + " Produto:Produto{Id=null, Nome:'null', Quantidade:null, Descrição:'null', Preço:null, Categoria:null,"
                + " Proprietário:null, Características:{}, Imagens:[]}, Cliente:Usuário{id:null, e-mail:'null', Cadastrado"
                + " em:null}}", actualToModelResult.toString());
    }
}

