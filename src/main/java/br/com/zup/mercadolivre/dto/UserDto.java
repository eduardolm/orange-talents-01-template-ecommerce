package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.User;

public class UserDto {

    private Long id;
    private String email;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.id = user.getId();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
