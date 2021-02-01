package br.com.zup.mercadolivre.mapper;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsMapper extends UserDetails {

    UserDetails map(Object shouldBeASystemUser);
}
