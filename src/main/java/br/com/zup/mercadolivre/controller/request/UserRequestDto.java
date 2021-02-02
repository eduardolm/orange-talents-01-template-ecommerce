package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.validator.UniqueValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRequestDto {

    @NotBlank(message = "O e-mail é obrigatório.")
    @UniqueValue(domainClass = User.class, fieldName = "email", message = "E-mail já cadastrado.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "O campo da senha não pode ser deixado em branco.")
    @Length(min = 6, message = "A senha deve conter no mínimo, 6 caracteres.")
    private String password;

    public UserRequestDto(@Valid @NotBlank(message = "O e-mail é obrigatório.")
                          @Email(message = "Formato de e-mail inválido.") String email,
                          @Valid @NotBlank(message = "O campo da senha não pode ser deixado em branco.")
                          @Length(min = 6, message = "A senha deve conter no mínimo, 6 caracteres.") String password) {

        this.email = email;
        this.password = password;
    }

    public User toModel(PasswordEncoder encoder) {
        return new User(email, encoder.encode(password));
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
