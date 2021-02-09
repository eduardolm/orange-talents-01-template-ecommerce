package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class ProductDetailDto {

    private Long id;
    private String name;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private Category category;
    private UserDto productOwner;
    private Set<ProductCharacteristicsDto> characteristics;
    private Set<String> images;
    private SortedSet<String> questions;
    private Set<Map<String, String>> reviews;
    private double averageGrade;
    private int total;

    public ProductDetailDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.productOwner = new UserDto(product.getProductOwner());
        this.characteristics = product.mapCharacteristics(ProductCharacteristicsDto::new);
        this.images = product.mapImages(ProductImage::getLink);
        this.questions = product.mapQuestions(ProductQuestion::getTitle);


        Reviews reviews = product.getReviews();
        this.reviews = reviews.mapReviews(productReview ->
                Map.of("title", productReview.getTitle(),
                        "description", productReview.getDescription()));

        this.averageGrade = reviews.average();
        this.total = reviews.total();
    }

    public Long getId() {
        return id;
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

    public Category getCategory() {
        return category;
    }

    public UserDto getProductOwner() {
        return productOwner;
    }

    public Set<ProductCharacteristicsDto> getCharacteristics() {
        return characteristics;
    }

    public Set<Map<String, String>> getReviews() {
        return reviews;
    }

    public SortedSet<String> getQuestions() {
        return questions;
    }

    public Set<String> getImages() {
        return images;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public int getTotal() {
        return total;
    }
}
