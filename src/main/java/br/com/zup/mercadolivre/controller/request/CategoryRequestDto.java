package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.validator.UniqueValue;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Optional;

public class CategoryRequestDto {

    private Long id;

    @NotBlank(message = "Nome da categoria é obrigatório.")
    @UniqueValue(domainClass = Category.class, fieldName = "name", message = "Categoria já cadastrada.")
    private String name;

    @Positive(message = "Id deve ser um inteiro positovo.")
    private Long idMotherCategory;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getIdMotherCategory() {
        return idMotherCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdMotherCategory(Long idMotherCategory) {
        this.idMotherCategory = idMotherCategory;
    }

    @Override
    public String toString() {
        return "CategoryRequestDto{" +
                "name='" + name + '\'' +
                ", idMotherCategory=" + idMotherCategory +
                '}';
    }

    public Category toModel(CategoryRepository repository) {
        Category category = new Category(name);
        if (idMotherCategory != null) {
            Optional<Category> motherCategory = repository.findById(idMotherCategory);
            Assert.notNull(motherCategory, "O id da categoria mãe precisa ser válido.");
            motherCategory.ifPresent(category::setMotherCategory);
        }
        return category;
    }
}
