package br.com.zup.mercadolivre.utils.builder;

import br.com.zup.mercadolivre.model.User;

public class UserBuilder {

    private String email;
    private String password;
    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(email, password);
    }
}
