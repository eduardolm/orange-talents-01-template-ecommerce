package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.utils.builder.CategoryBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void shouldCreateNewCategoryInstance() {
        Category category = new CategoryBuilder()
                .withName("Teste")
                .build();

        assertEquals("Teste", category.getName());
    }

    @Test
    public void shouldTwoCategoryInstancesNotBeEqualExceptIfEmpty() {
        Category category1 = new CategoryBuilder()
                .withName("Teste")
                .build();

        Category category2 = new CategoryBuilder()
                .withName("Testes")
                .build();

        Category category3 = new Category();
        Category category4 = new Category();

        assertNotEquals(category1.hashCode(), category2.hashCode());
        assertEquals(category3, category4);
        assertNotEquals(category3.hashCode(), category1.hashCode());
    }

    @Test
    public void shouldTwoIdenticalCategoriesBeEqual() {
        Category category1 = new CategoryBuilder()
                .withName("Teste")
                .build();

        Category category2 = new CategoryBuilder()
                .withName("Teste")
                .build();

        assertEquals(category2.hashCode(), category1.hashCode());
        assertEquals(category1, category2);
    }

    @Test
    public void shouldCategoryGettersWorkCorrectly() {
        Category category1 = new CategoryBuilder()
                .withName("Teste")
                .build();

        category1.setId(1L);
        category1.setParent(new Category("Parent Test"));

        assertEquals("Teste", category1.getName());
        assertEquals(1L, category1.getId());
        assertEquals("Parent Test", category1.getParent().getName());
    }
}
