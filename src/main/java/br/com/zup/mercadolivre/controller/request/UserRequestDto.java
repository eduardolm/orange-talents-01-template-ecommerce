package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.config.security.CryptoProperties;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.validator.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequestDto {

    @NotBlank(message = "O e-mail é obrigatório.")
    @UniqueValue(domainClass = User.class, fieldName = "email", message = "E-mail já cadastrado.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "O campo da senha não pode ser deixado em branco.")
    @Size(min = 6, max = 50, message = "A senha deve conter entre 6 e 50 caracteres.")
    private String password;

    public User toModel(CryptoProperties crypto) {
        return new User(email, crypto.convertToDatabaseColumn(password));
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
