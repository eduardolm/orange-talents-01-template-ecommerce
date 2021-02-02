package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.User;

public class UserDto {

    private Long id;
    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
