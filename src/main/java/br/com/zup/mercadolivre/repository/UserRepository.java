package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
