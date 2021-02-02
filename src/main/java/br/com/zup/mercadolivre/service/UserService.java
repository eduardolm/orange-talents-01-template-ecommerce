package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.config.security.UserPrincipal;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        var user = repository.findUserByEmail(username);
        Assert.notNull(user, "Usuário não encontrado.");

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(
                    "Não foi possível encontrar usuário com email: "
                            + username);
        }

        return new UserPrincipal(user.get());
    }

    public UserDetails map(String shouldBeASystemUser) {
        return loadUserByUsername(shouldBeASystemUser);
    }
}

