package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.CategoryRequestDto;
import br.com.zup.mercadolivre.dto.CategoryDto;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        Category category = categoryRequestDto.toModel(repository);
        return ResponseEntity.ok().body(new CategoryDto(repository.save(category)));
    }
}
