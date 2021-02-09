package br.com.zup.mercadolivre.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductReview;
import br.com.zup.mercadolivre.model.User;
import org.junit.jupiter.api.Test;

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
        assertEquals("ProductReview{Id:null, Nota:1, Titulo:'Dr', Descrição:'The characteristics of someone or something',"
                + " Produto:Produto{Id=null, Nome:'null', Quantidade:null, Descrição:'null', Preço:null, Categoria:null,"
                + " Proprietário:null, Características:{}, Imagens:[], Opiniões:[]}, Cliente:Usuário{id:null, e-mail:'null',"
                + " Cadastrado em:null}}", actualToModelResult.toString());
    }
}

