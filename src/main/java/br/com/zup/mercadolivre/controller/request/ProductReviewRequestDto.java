package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductReview;
import br.com.zup.mercadolivre.model.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductReviewRequestDto {

    @Min(value = 1, message = "O valor mínimo é 1.")
    @Max(value = 5, message = "O valor máximo é 5.")
    private Integer grade;

    @NotBlank(message = "O título é obrigatório.")
    private String title;

    @NotBlank(message = "A descrição é obrigatória.")
    @Length(max = 500, message = "O tamanho máximo da descrição é de 500 caracteres.")
    private String description;

    @NotNull(message = "O produto é obrigatório.")
    @Valid
    private Product product;

    @NotNull(message = "Usuário é obrigatório.")
    @Valid
    private User customer;

    public ProductReviewRequestDto(
            @Min(value = 1, message = "O valor mínimo é 1.")
            @Max(value = 5, message = "O valor máximo é 5.") Integer grade,
            @NotBlank(message = "O título é obrigatório.") String title,
            @NotBlank(message = "A descrição é obrigatória.")
            @Length(max = 500, message = "O tamanho máximo da descrição é de 500 caracteres.") String description) {

        super();
        this.grade = grade;
        this.title = title;
        this.description = description;
    }

    public ProductReview toModel(Product product, User customer) {
        return new ProductReview(grade, title, description, product, customer);
    }
}
