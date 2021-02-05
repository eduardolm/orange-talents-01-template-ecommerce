package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.config.IAuthenticationFacade;
import br.com.zup.mercadolivre.controller.request.ProductImageRequestDto;
import br.com.zup.mercadolivre.controller.request.ProductRequestDto;
import br.com.zup.mercadolivre.dto.ProductDetailDto;
import br.com.zup.mercadolivre.dto.ProductDto;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductImageRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.UserRepository;
import br.com.zup.mercadolivre.service.ProductImageService;
import br.com.zup.mercadolivre.validator.SameNameCharacteristicsValidator;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ProductImageService imageService;

    @InitBinder(value = "productRequestDto")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new SameNameCharacteristicsValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid ProductRequestDto productRequestDto) {
        User loggedUser = checkUserExists();
        Product product = productRequestDto.toModel(categoryRepository, loggedUser);
        return ResponseEntity.ok(new ProductDetailDto(productRepository.save(product)));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok().body(products.stream().map(ProductDto::new).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable() Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok().body(new ProductDetailDto(product.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("{id}/images")
    @Transactional
    public ResponseEntity<?> upload(@PathVariable("id") Long id,
                                    @Valid ProductImageRequestDto requestDto) throws IOException {

        User loggedUser = checkUserExists();
        Product product = checkProductExists(id);

        if (!product.belongsToUser(loggedUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Set<String> uploadedLinks = imageService.uploadImage(requestDto, product);
        Assert.notNull(uploadedLinks, "Imagens já existem em nosso banco de dados.");

        product.addImages(uploadedLinks);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/image/list")
    public ResponseEntity<?> listBuckets() {
        imageService.listBuckets();
        return ResponseEntity.ok().build();
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
