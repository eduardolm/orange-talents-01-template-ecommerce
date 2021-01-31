package br.com.zup.mercadolivre.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Usuário{" +
                "id:" + id +
                ", e-mail:'" + email + '\'' +
                ", Cadastrado em:" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        boolean emailEquals = (this.email == null && other.email == null)
                || (this.email != null && this.email.equals(other.email));
        boolean createdAtEquals = (this.createdAt == null && other.createdAt == null)
                || (this.createdAt != null && this.createdAt.equals(other.createdAt));
        return emailEquals && createdAtEquals;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        return result;
    }
}
