package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.validator.ExistsId;
import br.com.zup.mercadolivre.validator.UniqueValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ProductRequestDto {

    @NotBlank(message = "O nome é obrigatório.")
    @UniqueValue(domainClass = Product.class, fieldName = "name")
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
    @ExistsId(domainClass = Category.class, fieldName = "id")
    private Long categoryId;

    @Size(min = 3, message = "É preciso informar pelo menos três características do produto.")
    @Valid
    private List<CharacteristicsRequestDto> characteristics = new ArrayList<>();

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
                             @NotNull(message = "Obrigatório informar a categoria.") Long categoryId,
                             @Size(min = 3, message = "É preciso informar pelo menos três características do produto.")
                                     List<CharacteristicsRequestDto> characteristics) {

        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.characteristics.addAll(characteristics);
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

    public List<CharacteristicsRequestDto> getCharacteristics() {
        return characteristics;
    }

    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "Preço:'" + name + '\'' +
                ", Quantidade:" + quantity +
                ", Descrição:'" + description + '\'' +
                ", Preço:" + price +
                ", CategoriaId:" + categoryId +
                ", Características:" + characteristics.stream().map(item ->
                     item.getName() + ": " + item.getDescription()).collect(Collectors.toList()) +
                '}';
    }

    public Product toModel(CategoryRepository repository, User productOwner) {
        Category category = repository.findById(categoryId).orElseThrow(() ->
                new NoSuchElementException("Categoria não encontrada."));

        return new Product(name, quantity, description, price, category, productOwner, characteristics);
    }

    public Set<String> findRepeatedCharacteristics() {
        HashSet<String> equalNames = new HashSet<>();
        HashSet<String> result = new HashSet<>();
        for (CharacteristicsRequestDto characteristic : characteristics) {
            String name = characteristic.getName();
            if (!equalNames.add(name)) {
                result.add(name);
            }
        }
        return result;
    }
}
