package br.com.zup.mercadolivre.utils.builder;

import br.com.zup.mercadolivre.controller.request.ProductQuestionRequestDto;

public class QuestionRequestDtoBuilder {

    private String title;

    public QuestionRequestDtoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ProductQuestionRequestDto build() {
        return new ProductQuestionRequestDto(title);
    }
}
