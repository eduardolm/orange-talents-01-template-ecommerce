package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
public class CategoryDetailDto {

    private Long id;
    private String name;
    private Category parent;
    private List<Category> children;

    public CategoryDetailDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parent = category.getParent();
        this.children = category.getChildren();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
