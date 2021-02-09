package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.ProductQuestionRequestDto;
import br.com.zup.mercadolivre.dto.ProductQuestionDto;
import br.com.zup.mercadolivre.handler.ObjectHandler;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductQuestion;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.ProductQuestionRepository;
import br.com.zup.mercadolivre.service.EmailMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductQuestionController extends ObjectHandler {

    @Autowired
    private ProductQuestionRepository repository;

    @Autowired
    private EmailMessages email;

    @PostMapping("{id}/questions")
    @Transactional
    public ResponseEntity<?> create(@PathVariable("id") Long id,
                                    @RequestBody @Valid ProductQuestionRequestDto productQuestionRequestDto) {

        User loggedUser = checkUserExists();
        Product product = checkProductExists(id);
        ProductQuestion productQuestion = repository.save(productQuestionRequestDto.toModel(loggedUser, product));

        email.question(productQuestion);

        return ResponseEntity.ok().body(new ProductQuestionDto(productQuestion));
    }
}
