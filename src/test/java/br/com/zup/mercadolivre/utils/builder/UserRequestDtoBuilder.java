package br.com.zup.mercadolivre.utils.builder;

import br.com.zup.mercadolivre.controller.request.UserRequestDto;

public class UserRequestDtoBuilder {

    private String email;
    private String password;

    public UserRequestDtoBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRequestDtoBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserRequestDto build() {
        return new UserRequestDto(email, password);
    }
}
