package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.ProductImageRequestDto;
import br.com.zup.mercadolivre.controller.request.ProductRequestDto;
import br.com.zup.mercadolivre.dto.ProductDetailDto;
import br.com.zup.mercadolivre.handler.ObjectHandler;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.service.ProductImageService;
import br.com.zup.mercadolivre.validator.SameNameCharacteristicsValidator;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController extends ObjectHandler {

    @Autowired
    private CategoryRepository categoryRepository;

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

    @PostMapping("{id}/images")
    @Transactional
    public ResponseEntity<?> upload(@PathVariable("id") Long id,
                                    @Valid ProductImageRequestDto requestDto) throws Exception {

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


    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable() Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok().body(new ProductDetailDto(product.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
