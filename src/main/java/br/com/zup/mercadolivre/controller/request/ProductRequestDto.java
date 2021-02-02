package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class ProductRequestDto {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotNull(message = "A quantidade é obrigatória.")
    @PositiveOrZero(message = "A quantidade deve ser maior ou igual a zero.")
    private Integer quantity;

    @NotBlank(message = "A descrição é obrigatória.")
    @Length(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
    private String description;

    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "Preço precisa ser um valor positivo.")
    private BigDecimal price;

    @NotNull(message = "Obrigatório informar a categoria.")
    @Valid
    private Long categoryId;

    @Deprecated
    public ProductRequestDto(){}

    public ProductRequestDto(@NotBlank(message = "O nome é obrigatório.") String name,
                             @NotBlank(message = "A quantidade é obrigatória.")
                             @NotNull(message = "A quantidade é obrigatória.")
                             @PositiveOrZero(message = "A quantidade deve ser maior ou igual a zero.") Integer quantity,
                             @NotBlank(message = "A descrição é obrigatória.")
                             @Length(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.") String description,
                             @NotNull(message = "O preço é obrigatório.")
                             @Positive(message = "Preço precisa ser um valor positivo.") BigDecimal price,
                             @NotNull(message = "Obrigatório informar a categoria.") Long categoryId) {

        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "Nome:'" + name + '\'' +
                ", Quantidade:" + quantity +
                ", Descrição:'" + description + '\'' +
                ", Preço:" + price +
                ", CategoriaId:" + categoryId +
                '}';
    }

    public Product toModel(CategoryRepository repository, User productOwner) {
        Category category = repository.findById(categoryId).orElseThrow(() -> new NoSuchElementException("Categoria não encontrada."));
        return new Product(name, quantity, description, price, category, productOwner);
    }
}
