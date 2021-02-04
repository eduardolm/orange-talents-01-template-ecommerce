package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.ProductImageRequestDto;
import br.com.zup.mercadolivre.controller.request.ProductRequestDto;
import br.com.zup.mercadolivre.dto.ProductDetailDto;
import br.com.zup.mercadolivre.dto.ProductDto;
import br.com.zup.mercadolivre.handler.BucketHandler;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductImageRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.UserRepository;
import br.com.zup.mercadolivre.service.ProductImageService;
import br.com.zup.mercadolivre.validator.SameNameCharacteristicsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
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

    @Autowired
    private ProductImageRepository imageRepository;

    @Autowired
    private ProductImageService imageService;

    @InitBinder(value = "productRequestDto")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new SameNameCharacteristicsValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid ProductRequestDto productRequestDto) {
        // Simulando usuário logado
        User productOwner = userRepository.findUserByEmail("user@email.com").get();
        Product product = productRequestDto.toModel(categoryRepository, productOwner);
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
    public void upload(@PathVariable("id") Long id, @Valid ProductImageRequestDto requestDto) throws IOException {

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            imageService.createImage(requestDto, product.get());
        }
    }

    @GetMapping("{id}/image/list")
    public ResponseEntity<?> listBuckets() {
        imageService.listBuckets();
        return ResponseEntity.ok().build();
    }
}
