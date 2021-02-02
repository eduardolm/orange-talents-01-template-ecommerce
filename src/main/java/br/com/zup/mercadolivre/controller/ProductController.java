package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.ProductRequestDto;
import br.com.zup.mercadolivre.dto.ProductDto;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ProductRequestDto productRequestDto) {
        // Simulando usuário logado
        User productOwner = userRepository.findUserByEmail("user2@email.com").get();
        Product product = productRequestDto.toModel(categoryRepository, productOwner);
        return ResponseEntity.ok(new ProductDto(productRepository.save(product)));
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
            return ResponseEntity.ok().body(new ProductDto(product.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
