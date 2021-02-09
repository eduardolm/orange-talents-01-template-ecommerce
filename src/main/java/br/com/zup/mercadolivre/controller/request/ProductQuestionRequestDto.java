package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductQuestion;
import br.com.zup.mercadolivre.model.User;

import javax.validation.constraints.NotBlank;

public class ProductQuestionRequestDto {

    @NotBlank(message = "O título é obrigatório.")
    private String title;

    public ProductQuestionRequestDto(@NotBlank(message = "O título é obrigatório.") String title) {
        super();
        this.title = title;
    }

    @Deprecated
    public ProductQuestionRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "QuestionRequestDto{" +
                "Título:'" + title + '\'' +
                '}';
    }

    public ProductQuestion toModel(User interested, Product product) {
        return new ProductQuestion(title, interested, product);
    }
}
