package br.com.zup.mercadolivre.dto;

import br.com.zup.mercadolivre.model.Category;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductImage;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<ProductReviewDto> reviews;

    public ProductDetailDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.productOwner = new UserDto(product.getProductOwner());
        this.characteristics = product.getCharacteristics().stream().map(ProductCharacteristicsDto::new).collect(Collectors.toSet());
        this.images = product.getImages().stream().map(ProductImage::getLink).collect(Collectors.toSet());
        this.reviews = product.getProductReviews().stream().map(ProductReviewDto::new).collect(Collectors.toSet());
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

    public Set<ProductReviewDto> getReviews() {
        return reviews;
    }

    public Set<String> getImages() {
        return images;
    }
}
