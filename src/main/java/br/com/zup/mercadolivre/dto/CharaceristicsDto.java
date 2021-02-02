package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductCharacteristic;

public class CharaceristicsDto {
    public CharaceristicsDto(Object characteristics) {
    }

    public ProductCharacteristic toModel(Product product) {
        return new ProductCharacteristic(name, description, product);
    }
}
