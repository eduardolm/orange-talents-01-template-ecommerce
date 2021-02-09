package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.ProductReview;

public class ProductReviewDto {

    private String title;
    private Integer grade;
    private String description;

    public ProductReviewDto(ProductReview review) {
        this.title = review.getTitle();
        this.description = review.getDescription();
        this.grade = review.getGrade();
    }

    @Deprecated
    public ProductReviewDto() {}

    public String getTitle() {
        return title;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getDescription() {
        return description;
    }
}
