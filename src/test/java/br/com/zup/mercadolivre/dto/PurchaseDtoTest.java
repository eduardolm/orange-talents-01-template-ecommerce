package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.enums.PaymentGateway;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PurchaseDtoTest {
    @Test
    public void testConstructor() {
        Product product = new Product();

        PurchaseDto actualPurchaseDto = new PurchaseDto(new Purchase(product, 1, new User(), PaymentGateway.PAG_SEGURO));

        assertEquals("PurchaseDto{Id:null, Produto:ProductDto{Id:null, Nome:'null', Quantidade:null, Descrição:'null',"
                + " Preço:null}, Quantidade:1, Pagamento:PAG_SEGURO}", actualPurchaseDto.toString());
        assertNull(actualPurchaseDto.getId());
        assertEquals(1, actualPurchaseDto.getQuantity());
        ProductDto product1 = actualPurchaseDto.getProduct();
        assertNull(product1.getPrice());
        assertNull(product1.getId());
        assertNull(product1.getQuantity());
        assertNull(product1.getDescription());
        assertNull(product1.getName());
    }

    @Test
    public void testConstructor2() {
        PurchaseDto actualPurchaseDto = new PurchaseDto(123L, new Product(), 1, PaymentGateway.PAG_SEGURO);

        assertEquals("PurchaseDto{Id:123, Produto:ProductDto{Id:null, Nome:'null', Quantidade:null, Descrição:'null',"
                + " Preço:null}, Quantidade:1, Pagamento:PAG_SEGURO}", actualPurchaseDto.toString());
        assertEquals(123L, actualPurchaseDto.getId().longValue());
        assertEquals(1, actualPurchaseDto.getQuantity());
        ProductDto product = actualPurchaseDto.getProduct();
        assertNull(product.getPrice());
        assertNull(product.getId());
        assertNull(product.getQuantity());
        assertNull(product.getDescription());
        assertNull(product.getName());
    }

    @Test
    public void testConstructor3() {
        PurchaseDto actualPurchaseDto = new PurchaseDto(123L, new Product(), 0, PaymentGateway.PAG_SEGURO);

        assertEquals("PurchaseDto{Id:123, Produto:ProductDto{Id:null, Nome:'null', Quantidade:null, Descrição:'null',"
                + " Preço:null}, Quantidade:0, Pagamento:PAG_SEGURO}", actualPurchaseDto.toString());
        assertEquals(123L, actualPurchaseDto.getId().longValue());
        assertEquals(0, actualPurchaseDto.getQuantity());
        ProductDto product = actualPurchaseDto.getProduct();
        assertNull(product.getPrice());
        assertNull(product.getId());
        assertNull(product.getQuantity());
        assertNull(product.getDescription());
        assertNull(product.getName());
    }

    @Test
    public void testToString() {
        Product product = new Product();

        assertEquals(
                "PurchaseDto{Id:null, Produto:ProductDto{Id:null, Nome:'null', Quantidade:null, Descrição:'null',"
                        + " Preço:null}, Quantidade:1, Pagamento:PAG_SEGURO}",
                (new PurchaseDto(new Purchase(product, 1, new User(), PaymentGateway.PAG_SEGURO))).toString());
    }
}

