package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.ProductReviewRequestDto;
import br.com.zup.mercadolivre.handler.ObjectHandler;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductReviewController extends ObjectHandler {

    @Autowired
    protected ProductReviewRepository reviewRepository;

    @PostMapping("{id}/review")
    @Transactional
    public ResponseEntity<?> create(@PathVariable("id") Long id,  @RequestBody @Valid ProductReviewRequestDto requestDto) {
        User loggedUser = checkUserExists();
        Product product = checkProductExists(id);

        return ResponseEntity.ok().body(reviewRepository.save(requestDto.toModel(product, loggedUser)));
    }
}
