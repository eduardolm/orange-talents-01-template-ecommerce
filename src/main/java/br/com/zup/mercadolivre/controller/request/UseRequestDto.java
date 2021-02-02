package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.validator.UniqueValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UseRequestDto {

    @NotBlank(message = "O e-mail é obrigatório.")
    @UniqueValue(domainClass = User.class, fieldName = "email", message = "E-mail já cadastrado.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6)
    private String password;

    public User toModel() {
        return new User(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
