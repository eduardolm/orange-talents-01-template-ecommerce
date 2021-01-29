package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
