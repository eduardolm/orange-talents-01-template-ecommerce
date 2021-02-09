package br.com.zup.mercadolivre.handler;

import br.com.zup.mercadolivre.config.IAuthenticationFacade;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ObjectHandler {

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    protected User checkUserExists() {
        Optional<User> loggedUser = userRepository.findUserByEmail(getAuthenticatedUser());
        if (loggedUser.isEmpty()) {
            throw  new NoSuchElementException("Usuário não encontrado.");
        }
        else {
            return loggedUser.get();
        }
    }

    protected Product checkProductExists(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw  new NoSuchElementException("Produto não encontrado.");
        }
        else {
            return product.get();
        }
    }

    protected String getAuthenticatedUser() {
        return authenticationFacade.getAuthentication().getName();
    }
}
