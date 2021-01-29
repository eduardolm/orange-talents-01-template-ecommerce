package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Category;

public class CategoryDto {
    private Long id;

    private String name;

    private String motherCategory;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        if (category.getMotherCategory() != null) {
            this.motherCategory = category.getMotherCategory().getName();
        }
        else {
            this.motherCategory = "Não possui categoria mãe.";
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMotherCategory() {
        return motherCategory;
    }
}
