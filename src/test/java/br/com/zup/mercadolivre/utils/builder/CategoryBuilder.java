package br.com.zup.mercadolivre.utils.builder;

import br.com.zup.mercadolivre.model.Category;

public class CategoryBuilder {

    private String name;
    private Long parentCategoryId;

    public CategoryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Category build() {
        return new Category(name);
    }
}
