package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository  extends JpaRepository<ProductReview, Long> {
}
