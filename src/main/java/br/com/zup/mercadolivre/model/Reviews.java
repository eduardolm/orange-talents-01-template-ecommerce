package br.com.zup.mercadolivre.model;

import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Reviews {

    private Set<ProductReview> productReviews;

    public Reviews(Set<ProductReview> productReviews) {

        this.productReviews = productReviews;
    }

    public <T> Set<T> mapReviews(Function<ProductReview, T> mapperFunction) {
        return this.productReviews
                .stream()
                .map(mapperFunction)
                .collect(Collectors.toSet());
    }

    public double average() {
        Set<Integer> grades = mapReviews(review -> review.getGrade());
        OptionalDouble possibleAvg = grades.stream().mapToInt(grade -> grade).average();
        return possibleAvg.orElse(0.0);
    }

    public int total() {
        return this.productReviews.size();
    }
}
