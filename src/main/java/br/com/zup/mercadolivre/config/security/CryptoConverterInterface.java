package br.com.zup.mercadolivre.config.security;

public interface CryptoConverterInterface {

    String convertToDatabaseColumn(String inputValue);
    String convertToEntityAttribute(String dbData);
}
