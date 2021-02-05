package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.config.IAuthenticationFacade;
import br.com.zup.mercadolivre.controller.request.ProductReviewRequestDto;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.ProductReviewRepository;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductReviewController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductReviewRepository reviewRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @PostMapping("{id}/review")
    @Transactional
    public ResponseEntity<?> create(@PathVariable("id") Long id,  @RequestBody @Valid ProductReviewRequestDto requestDto) {
        User loggedUser = checkUserExists();
        Product product = checkProductExists(id);

        return ResponseEntity.ok().body(reviewRepository.save(requestDto.toModel(product, loggedUser)));
    }

    private User checkUserExists() {
        Optional<User> loggedUser = userRepository.findUserByEmail(getAuthenticatedUser());
        if (loggedUser.isEmpty()) {
            throw  new NoSuchElementException("Usuário não encontrado.");
        }
        else {
            return loggedUser.get();
        }
    }

    private Product checkProductExists(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw  new NoSuchElementException("Produto não encontrado.");
        }
        else {
            return product.get();
        }
    }

    private String getAuthenticatedUser() {
        return authenticationFacade.getAuthentication().getName();
    }
}
