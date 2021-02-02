package br.com.zup.mercadolivre.config.security;

public interface CryptoPropertiesInterface {

    String convertToDatabaseColumn(String inputValue);
    String convertToEntityAttribute(String dbData);
}
