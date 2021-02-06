package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.ProductQuestion;

import java.time.LocalDate;

public class ProductQuestionDto {

    private Long id;
    private String title;
    private String product;
    private String interested;
    private LocalDate createdAt;

    public ProductQuestionDto(ProductQuestion productQuestion) {
        this.id = productQuestion.getId();
        this.title = productQuestion.getTitle();
        this.product = productQuestion.getProduct().getName();
        this.interested = productQuestion.getInterested().getEmail();
        this.createdAt = productQuestion.getCreatedAt();
    }

    @Deprecated
    public ProductQuestionDto() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getProduct() {
        return product;
    }

    public String getInterested() {
        return interested;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
