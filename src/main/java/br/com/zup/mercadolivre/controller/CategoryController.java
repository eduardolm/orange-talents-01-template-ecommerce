package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.CategoryRequestDto;
import br.com.zup.mercadolivre.dto.CategoryDto;
import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Category> categories = repository.findAllByParentEquals(null);
        return ResponseEntity.ok().body(categories.stream().map(CategoryDto::new).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable() Long id) {
        Optional<Category> response = repository.findById(id);
        if (response.isPresent()) {
            return ResponseEntity.ok().body(new CategoryDto(response.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
