package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.validator.UniqueValue;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CategoryRequestDto {

    @NotBlank(message = "Nome da categoria é obrigatório.")
    @UniqueValue(domainClass = Category.class, fieldName = "name", message = "Categoria já cadastrada.")
    private String name;

    @Positive(message = "Id deve ser um inteiro positivo.")
    private Long idParentCategory;

    public CategoryRequestDto() {
    }

    public CategoryRequestDto(@NotBlank(message = "Nome da categoria é obrigatório.") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getIdParentCategory() {
        return idParentCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdParentCategory(Long idParentCategory) {
        this.idParentCategory = idParentCategory;
    }

    @Override
    public String toString() {
        return "CategoryRequestDto{" +
                "Categoria='" + name + '\'' +
                ", idCategoriaMae=" + idParentCategory +
                '}';
    }

    public Category toModel(CategoryRepository repository) {
        Category category = new Category(name);
        if (idParentCategory != null) {
            Optional<Category> parentCategory = repository.findById(idParentCategory);
            Assert.notNull(parentCategory, "O id da categoria mãe precisa ser válido.");
            if (parentCategory.isPresent()) {
                parentCategory.get().getChildren().add(category);
                repository.save(parentCategory.get());
                category.setParent(parentCategory.get());
            }
            else {
                throw new NoSuchElementException("Categoria mãe não encontrada.");
            }
        }
        return category;
    }
}
