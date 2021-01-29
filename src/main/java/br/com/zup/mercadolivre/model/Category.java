package br.com.zup.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "categorias")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private Category motherCategory;

    public Category(@NotBlank String name) {
        this.name = name;
    }

    public Category(@NotBlank String name, Category motherCategory) {
        this.name = name;
        this.motherCategory = motherCategory;
    }

    public Category() {

    }

    public void setMotherCategory(Category motherCategory) {
        this.motherCategory = motherCategory;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getMotherCategory() {
        return motherCategory;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((motherCategory.getId() == null) ? 0 : motherCategory.getId().hashCode());
        result = prime * result + ((motherCategory.getName() == null) ? 0 : motherCategory.getName().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Category)) {
            return false;
        }
        Category other = (Category) obj;
        return (Objects.equals(id, other.id))
                && (Objects.equals(name, other.name))
                && (Objects.equals(motherCategory.getId(), other.motherCategory.getId()))
                && (Objects.equals(motherCategory.getName(), other.motherCategory.getName()));
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "Nome:'" + name + '\'' +
                ", Categoria mãe:" + motherCategory.name +
                '}';
    }
}
