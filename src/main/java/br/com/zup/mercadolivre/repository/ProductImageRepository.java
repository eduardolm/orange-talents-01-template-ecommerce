package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
